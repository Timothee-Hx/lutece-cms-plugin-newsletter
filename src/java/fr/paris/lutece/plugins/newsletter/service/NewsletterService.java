/*
 * Copyright (c) 2002-2012, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.newsletter.service;

import fr.paris.lutece.plugins.newsletter.business.NewsLetter;
import fr.paris.lutece.plugins.newsletter.business.NewsLetterHome;
import fr.paris.lutece.plugins.newsletter.business.Subscriber;
import fr.paris.lutece.plugins.newsletter.business.SubscriberHome;
import fr.paris.lutece.plugins.newsletter.business.section.NewsletterSection;
import fr.paris.lutece.plugins.newsletter.business.section.NewsletterSectionHome;
import fr.paris.lutece.plugins.newsletter.service.section.NewsletterSectionService;
import fr.paris.lutece.plugins.newsletter.util.NewsLetterConstants;
import fr.paris.lutece.plugins.newsletter.util.NewsletterUtils;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.mail.MailService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.mail.UrlAttachment;
import fr.paris.lutece.util.string.StringUtil;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import au.com.bytecode.opencsv.CSVWriter;


/**
 * The newsletter service
 * 
 */
public class NewsletterService
{
    /**
     * Name of the bean of this service
     */
    public static final String BEAN_NAME = "newsletter.newsletterService";

    // PROPERTIES
    private static final String PROPERTY_ABSOLUTE_URL_MAIL = "newsletter.absolute.mail.url";
    private static final String PROPERTY_PATH_IMAGE_NEWSLETTER_TEMPLATE = "newsletter.path.image.newsletter.template";
    private static final String PROPERTY_NO_SECURED_IMG_FOLDER = "newsletter.nosecured.img.folder.name";
    private static final String PROPERTY_WEBAPP_PATH = "newsletter.nosecured.webapp.path";
    private static final String PROPERTY_WEBAPP_URL = "newsletter.nosecured.webapp.url";
    private static final String PROPERTY_NO_SECURED_IMG_OPTION = "newsletter.nosecured.img.option";

    private NewsletterSectionService _newsletterSectionService;
    private Plugin _plugin;

    /**
     * Returns the instance of the singleton
     * 
     * @return The instance of the singleton
     */
    public static NewsletterService getService( )
    {
        return SpringContextService.getBean( BEAN_NAME );
    }

    /**
     * Send the newsletter to a list of subscribers
     * @param newsletter The newsletter to send
     * @param strObject The email object
     * @param strBaseUrl The baseUrl (can be prod url)
     * @param templateNewsletter The generated template
     * @param listSubscribers The list of subscribers (date and id can be null,
     *            only email is used)
     */
    public void sendMail( NewsLetter newsletter, String strObject, String strBaseUrl, HtmlTemplate templateNewsletter,
            Collection<Subscriber> listSubscribers )
    {
        List<UrlAttachment> urlAttachments = null;

        if ( isMhtmlActivated( ) )
        {
            // we use absolute urls if there is no preproduction process
            boolean useAbsoluteUrl = isAbsoluteUrl( );
            String strTemplate = templateNewsletter.getHtml( );
            strTemplate = StringUtil.substitute( strTemplate, strBaseUrl,
                    NewsLetterConstants.WEBAPP_PATH_FOR_LINKSERVICE );
            urlAttachments = MailService.getUrlAttachmentList( strTemplate, strBaseUrl, useAbsoluteUrl );

            // all images, css urls are relative
            if ( !useAbsoluteUrl )
            {
                templateNewsletter.substitute( strBaseUrl, strBaseUrl.replaceFirst( "https?://[^/]+/", "/" ) );
            }
            else
            {
                String strContent = NewsletterUtils.rewriteUrls( templateNewsletter.getHtml( ), strBaseUrl );
                templateNewsletter = new HtmlTemplate( strContent );
            }
        }

        for ( Subscriber subscriber : listSubscribers )
        {
            HtmlTemplate t = new HtmlTemplate( templateNewsletter );
            t.substitute( NewsLetterConstants.MARK_SUBSCRIBER_EMAIL_EACH, subscriber.getEmail( ) );

            String strNewsLetterCode = t.getHtml( );

            if ( ( urlAttachments == null ) || ( urlAttachments.size( ) == 0 ) )
            {
                MailService.sendMailHtml( subscriber.getEmail( ), newsletter.getNewsletterSenderName( ),
                        newsletter.getNewsletterSenderMail( ), strObject, strNewsLetterCode );
            }
            else
            {
                MailService.sendMailMultipartHtml( subscriber.getEmail( ), newsletter.getNewsletterSenderName( ),
                        newsletter.getNewsletterSenderMail( ), strObject, strNewsLetterCode, urlAttachments );
            }
        }
    }

    /**
     * Check the property in property file to know if url must be absolutes or
     * relatives
     * @return true if absolute or false else
     */
    public boolean isAbsoluteUrl( )
    {
        boolean useAbsoluteUrl = false;
        String strUseAbsoluteUrl = AppPropertiesService.getProperty( PROPERTY_ABSOLUTE_URL_MAIL );

        if ( ( strUseAbsoluteUrl != null ) && strUseAbsoluteUrl.equalsIgnoreCase( Boolean.TRUE.toString( ) ) )
        {
            useAbsoluteUrl = true;
        }

        return useAbsoluteUrl;
    }

    /**
     * Determine if mails must be sent in MHTML
     * @return true whether MHTML is needed
     */
    public boolean isMhtmlActivated( )
    {
        String strProperty = AppPropertiesService.getProperty( NewsLetterConstants.PROPERTY_MAIL_MULTIPART );
        return ( strProperty != null ) && Boolean.valueOf( strProperty ).booleanValue( );
    }

    /**
     * Fetches the list of subscribers on a specific newsletter
     * @param nNewsletterId The id of the newsletter
     * @return The byte representation of the list of subscribers
     */
    public byte[] getSubscribersCsvExport( int nNewsletterId )
    {
        byte[] byteSubscribersList = null;

        try
        {
            ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream( );
            CSVWriter writer = new CSVWriter( new BufferedWriter( new OutputStreamWriter( byteArrayStream, "UTF-8" ) ) );
            Collection<Subscriber> listSubscriber = SubscriberHome.findSubscribers( nNewsletterId, getPlugin( ) );

            for ( Subscriber subscriber : listSubscriber )
            {
                String[] arraySubscriber = new String[3];
                arraySubscriber[0] = Integer.toString( subscriber.getId( ) );
                arraySubscriber[1] = subscriber.getEmail( );
                arraySubscriber[2] = subscriber.getDateSubscription( ).toString( );
                writer.writeNext( arraySubscriber );
            }

            writer.close( );
            byteSubscribersList = byteArrayStream.toByteArray( );
        }
        catch ( UnsupportedEncodingException e )
        {
            AppLogService.error( e );
        }
        catch ( IOException e )
        {
            AppLogService.error( e );
        }

        return byteSubscribersList;
    }

    /**
     * Remove a known suscriber from a newsletter
     * @param subscriber the subscriber to remove
     * @param nNewsletterId the newsletter id from which to remove the
     *            subscriber
     * @param plugin The plugin object
     */
    public void removeSubscriberFromNewsletter( Subscriber subscriber, int nNewsletterId, Plugin plugin )
    {
        /* checks newsletter exist in database */
        NewsLetter newsletter = NewsLetterHome.findByPrimaryKey( nNewsletterId, plugin );

        if ( ( subscriber != null ) && ( newsletter != null ) )
        {
            int nSubscriberId = subscriber.getId( );

            /* checks if the subscriber identified is registered */
            if ( NewsLetterHome.findRegistration( nNewsletterId, nSubscriberId, plugin ) )
            {
                /* unregistration */
                NewsLetterHome.removeSubscriber( nNewsletterId, nSubscriberId, plugin );
            }

            /*
             * if the subscriber is not registered to an other newsletter, his
             * account is deleted
             */
            if ( SubscriberHome.findNewsLetters( nSubscriberId, plugin ) == 0 )
            {
                SubscriberHome.remove( nSubscriberId, plugin );
            }
        }
    }

    /**
     * Generate the html code of the newsletter according to the document and
     * newsletter templates
     * 
     * @param newsletter the newsletter to generate the HTML of
     * @param nTemplateNewsLetterId the newsletter template id
     * @param strBaseUrl The base url of the portal
     * @param user the current user
     * @param locale The locale
     * @return the html code for the newsletter content of null if no template
     *         available
     */
    public String generateNewsletterHtmlCode( NewsLetter newsletter, int nTemplateNewsLetterId, String strBaseUrl,
            AdminUser user, Locale locale )
    {
        String strTemplatePath = NewsletterUtils.getHtmlTemplatePath( nTemplateNewsLetterId, getPlugin( ) );
        //        String strDocumentPath = generateDocumentsList( nNewsLetterId, nTemplateDocumentId, strBaseUrl );

        if ( strTemplatePath == null )
        {
            return null;
        }

        Map<String, Object> model = new HashMap<String, Object>( );
        List<NewsletterSection> listSections = NewsletterSectionHome.findAllByIdNewsletter( newsletter.getId( ),
                getPlugin( ) );

        // We sort the elements so that they are ordered by category and order.
        Collections.sort( listSections );

        int nCurrentCategory = 0;
        String[] strContentByCategory = new String[newsletter.getNbCategories( )];
        List<NewsletterSection> listSelectedSections = new ArrayList<NewsletterSection>( );
        for ( int i = 0; i < listSections.size( ) + 1; i++ )
        {
            NewsletterSection newsletterSection = null;
            if ( i < listSections.size( ) )
            {
                newsletterSection = listSections.get( i );
            }
            if ( newsletterSection != null && newsletterSection.getCategory( ) == nCurrentCategory )
            {
                listSelectedSections.add( newsletterSection );
            }
            else
            {
                if ( nCurrentCategory != 0 )
                {
                    StringBuilder sbCategoryContent = new StringBuilder( );
                    for ( NewsletterSection section : listSelectedSections )
                    {
                        sbCategoryContent.append( getNewsletterSectionService( ).getSectionContent( section, user,
                                locale ) );
                    }
                    strContentByCategory[nCurrentCategory - 1] = sbCategoryContent.toString( );
                }
                if ( newsletterSection != null )
                {
                    nCurrentCategory = newsletterSection.getCategory( );
                    listSelectedSections = new ArrayList<NewsletterSection>( );
                    listSelectedSections.add( newsletterSection );
                }
            }
        }

        model.put( NewsLetterConstants.MARK_CONTENT, strContentByCategory[0] );
        for ( int i = 0; i < strContentByCategory.length; i++ )
        {
            model.put( NewsLetterConstants.MARK_CONTENT_CATEGORY + Integer.toString( i + 1 ), strContentByCategory[i] );
        }
        model.put( NewsLetterConstants.MARK_BASE_URL, strBaseUrl );

        HtmlTemplate templateNewsLetter = AppTemplateService.getTemplate( strTemplatePath, locale, model );

        return templateNewsLetter.getHtml( );
    }

    /**
     * Get the url of the image folder used by templates
     * @param strBaseUrl The base url
     * @return The absolute url of the folder containing images of templates.
     */
    public String getImageFolderPath( String strBaseUrl )
    {
        return strBaseUrl + AppPropertiesService.getProperty( PROPERTY_PATH_IMAGE_NEWSLETTER_TEMPLATE );
    }

    /**
     * Check if images of the newsletter should be transfered on an unsecured
     * webapp or not
     * @return True if images of the newsletter should be transfered on an
     *         unsecured webapp, false otherwise
     */
    public boolean useUnsecuredImages( )
    {
        return Boolean.parseBoolean( AppPropertiesService.getProperty( PROPERTY_NO_SECURED_IMG_OPTION ) );
    }

    /**
     * Get the unsecured image folder inside the unsecured folder
     * @return The unsecured image folder inside the unsecured folder
     */
    public String getUnsecuredImagefolder( )
    {
        return AppPropertiesService.getProperty( PROPERTY_NO_SECURED_IMG_FOLDER ) + NewsLetterConstants.CONSTANT_SLASH;
    }

    /**
     * Get the absolute path to the unsecured folder where files should be
     * saved
     * @return The absolute path to the unsecured folder where files should be
     *         saved, or the webapp path if none is defined
     */
    public String getUnsecuredFolderPath( )
    {
        return AppPropertiesService.getProperty( PROPERTY_WEBAPP_PATH, AppPathService.getWebAppPath( )
                + NewsLetterConstants.CONSTANT_SLASH );
    }

    /**
     * Get the absolute url to the unsecured webapp.
     * @return The absolute url to the unsecured webapp, or the base url of this
     *         webapp if none is defined
     */
    public String getUnsecuredWebappUrl( )
    {
        return AppPropertiesService.getProperty( PROPERTY_WEBAPP_URL, AppPathService.getBaseUrl( ) );
    }

    /**
     * Get the instance of the newsletter plugin
     * @return the instance of the newsletter plugin
     */
    private Plugin getPlugin( )
    {
        if ( _plugin == null )
        {
            _plugin = PluginService.getPlugin( NewsletterPlugin.PLUGIN_NAME );
        }
        return _plugin;
    }

    /**
     * Get the NewsletterSectionService instance of this service
     * @return The NewsletterSectionService instance of this service
     */
    private NewsletterSectionService getNewsletterSectionService( )
    {
        if ( _newsletterSectionService == null )
        {
            _newsletterSectionService = NewsletterSectionService.getService( );
        }
        return _newsletterSectionService;
    }
}
