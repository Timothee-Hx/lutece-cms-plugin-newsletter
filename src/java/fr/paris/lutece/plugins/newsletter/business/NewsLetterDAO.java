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
package fr.paris.lutece.plugins.newsletter.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;


/**
 * This class provides Data Access methods for NewsLetter objects
 */
public final class NewsLetterDAO implements INewsLetterDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT name, description, date_last_send, html, id_newsletter_template, workgroup_key, unsubscribe, sender_mail, sender_name, test_recipients, test_subject, nb_categories  FROM newsletter_description WHERE id_newsletter = ? ";
    private static final String SQL_QUERY_SELECT_ALL = "SELECT id_newsletter , name, description, date_last_send, html, id_newsletter_template, workgroup_key, test_recipients , sender_mail, test_subject, nb_categories FROM newsletter_description ";
    private static final String SQL_QUERY_SELECT_ALL_ID = "SELECT id_newsletter, name FROM newsletter_description ";
    private static final String SQL_QUERY_SELECT_NBR_SUBSCRIBERS = "SELECT count(*) FROM newsletter_subscriber a, newsletter_subscriber_details b WHERE a.id_subscriber = b.id_subscriber AND b.email LIKE ? AND id_newsletter = ? ";
    private static final String SQL_QUERY_SELECT_NBR_ACTIVE_SUBSCRIBERS = "SELECT count(*) FROM newsletter_subscriber a, newsletter_subscriber_details b WHERE a.id_subscriber = b.id_subscriber AND b.email LIKE ? AND id_newsletter = ? AND a.confirmed = 1";
    private static final String SQL_QUERY_UPDATE = "UPDATE newsletter_description SET name = ?, description = ?, date_last_send = ?, html = ?, id_newsletter_template = ?, workgroup_key = ? , unsubscribe = ? ,sender_mail = ? ,sender_name = ? , test_recipients = ?, test_subject = ?, nb_categories = ? WHERE id_newsletter = ? ";
    private static final String SQL_QUERY_INSERT = "INSERT INTO newsletter_description ( id_newsletter , name, description, date_last_send, html, id_newsletter_template, workgroup_key, unsubscribe, sender_mail, sender_name, test_recipients , test_subject, nb_categories ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ? )";
    private static final String SQL_QUERY_INSERT_SUBSCRIBER = "INSERT INTO newsletter_subscriber ( id_newsletter , id_subscriber, date_subscription, confirmed ) VALUES ( ?, ?, ?, ? )";
    private static final String SQL_QUERY_VALIDATE_SUBSCRIBER = "UPDATE newsletter_subscriber SET confirmed = 1 WHERE id_newsletter = ? AND id_subscriber = ?";
    private static final String SQL_QUERY_DELETE = "DELETE FROM newsletter_description WHERE id_newsletter = ? ";
    private static final String SQL_QUERY_DELETE_FROM_SUBSCRIBER = "DELETE FROM newsletter_subscriber WHERE id_newsletter = ? and id_subscriber = ? ";
    private static final String SQL_QUERY_DELETE_OLD_FROM_SUBSCRIBER = "DELETE FROM newsletter_subscriber WHERE date_subscription < ? and confirmed = ? ";
    private static final String SQL_QUERY_CHECK_PRIMARY_KEY = "SELECT id_newsletter FROM newsletter_description WHERE id_newsletter = ?";
    private static final String SQL_QUERY_CHECK_LINKED_PORTLET = "SELECT id_newsletter FROM  newsletter_portlet_subscribe WHERE id_newsletter = ?";
    private static final String SQL_QUERY_NEW_PRIMARY_KEY = "SELECT max(id_newsletter) FROM newsletter_description ";
    private static final String SQL_QUERY_CHECK_IS_REGISTERED = "SELECT id_newsletter FROM newsletter_subscriber WHERE id_newsletter = ? AND id_subscriber = ? ";
    private static final String SQL_QUERY_CHECK_IS_TEMPLATE_USED = "SELECT id_newsletter FROM newsletter_description WHERE id_newsletter_template = ? ";
    private static final String SQL_QUERY_DELETE_UNUSED_EMAIL = "DELETE FROM newsletter_subscriber_details WHERE id_subscriber NOT IN (SELECT id_subscriber FROM newsletter_subscriber)";

    ///////////////////////////////////////////////////////////////////////////////////////
    //Access methods to data

    /**
     * Insert a new record in the table.
     * 
     * @param newsLetter the object to insert
     * @param plugin the Plugin
     */
    public void insert( NewsLetter newsLetter, Plugin plugin )
    {
        int nNewPrimaryKey = newPrimaryKey( plugin );
        newsLetter.setId( nNewPrimaryKey );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        int nIndex = 1;
        daoUtil.setInt( nIndex++, newsLetter.getId( ) );
        daoUtil.setString( nIndex++, newsLetter.getName( ) );
        daoUtil.setString( nIndex++, newsLetter.getDescription( ) );
        daoUtil.setTimestamp( nIndex++, newsLetter.getDateLastSending( ) );
        daoUtil.setString( nIndex++, newsLetter.getHtml( ) );
        daoUtil.setInt( nIndex++, newsLetter.getNewsLetterTemplateId( ) );
        daoUtil.setString( nIndex++, newsLetter.getWorkgroup( ) );
        daoUtil.setString( nIndex++, newsLetter.getUnsubscribe( ) );
        daoUtil.setString( nIndex++, newsLetter.getNewsletterSenderMail( ) );
        daoUtil.setString( nIndex++, newsLetter.getNewsletterSenderName( ) );
        daoUtil.setString( nIndex++, newsLetter.getTestRecipients( ) );
        daoUtil.setString( nIndex++, newsLetter.getTestSubject( ) );
        daoUtil.setInt( nIndex, newsLetter.getNbCategories( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * Remove a record from the table
     * 
     * @param nNewsLetterId the newsletter identifier
     * @param plugin the Plugin
     */
    public void delete( int nNewsLetterId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nNewsLetterId );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * loads the data of the newsletter from the table
     * 
     * @param nNewsLetterId the newsletter identifier
     * @param plugin the Plugin
     * @return the object inserted
     */
    public NewsLetter load( int nNewsLetterId, Plugin plugin )
    {
        NewsLetter newsLetter = new NewsLetter( );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nNewsLetterId );
        daoUtil.executeQuery( );

        if ( daoUtil.next( ) )
        {
            int nIndex = 1;
            newsLetter.setId( nNewsLetterId );
            newsLetter.setName( daoUtil.getString( nIndex++ ) );
            newsLetter.setDescription( daoUtil.getString( nIndex++ ) );
            newsLetter.setDateLastSending( daoUtil.getTimestamp( nIndex++ ) );
            newsLetter.setHtml( daoUtil.getString( nIndex++ ) );
            newsLetter.setNewsLetterTemplateId( daoUtil.getInt( nIndex++ ) );
            newsLetter.setWorkgroup( daoUtil.getString( nIndex++ ) );
            newsLetter.setUnsubscribe( daoUtil.getString( nIndex++ ) );
            newsLetter.setNewsletterSenderMail( daoUtil.getString( nIndex++ ) );
            newsLetter.setNewsletterSenderName( daoUtil.getString( nIndex++ ) );
            newsLetter.setTestRecipients( daoUtil.getString( nIndex++ ) );
            newsLetter.setTestSubject( daoUtil.getString( nIndex++ ) );
            newsLetter.setNbCategories( daoUtil.getInt( nIndex ) );
        }

        daoUtil.free( );

        return newsLetter;
    }

    /**
     * Update the record in the table
     * 
     * @param newsLetter the object to be updated
     * @param plugin the Plugin
     */
    public void store( NewsLetter newsLetter, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;
        daoUtil.setString( nIndex++, newsLetter.getName( ) );
        daoUtil.setString( nIndex++, newsLetter.getDescription( ) );
        daoUtil.setTimestamp( nIndex++, newsLetter.getDateLastSending( ) );
        daoUtil.setString( nIndex++, newsLetter.getHtml( ) );
        daoUtil.setInt( nIndex++, newsLetter.getNewsLetterTemplateId( ) );
        daoUtil.setString( nIndex++, newsLetter.getWorkgroup( ) );
        daoUtil.setString( nIndex++, newsLetter.getUnsubscribe( ) );
        daoUtil.setString( nIndex++, newsLetter.getNewsletterSenderMail( ) );
        daoUtil.setString( nIndex++, newsLetter.getNewsletterSenderName( ) );
        daoUtil.setString( nIndex++, newsLetter.getTestRecipients( ) );
        daoUtil.setString( nIndex++, newsLetter.getTestSubject( ) );
        daoUtil.setInt( nIndex++, newsLetter.getNbCategories( ) );
        daoUtil.setInt( nIndex, newsLetter.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * Check the unicity of the primary key
     * 
     * @param nKey the key to be checked
     * @param plugin the Plugin
     * @return true if the identifier exist and false if not
     */
    public boolean checkPrimaryKey( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_CHECK_PRIMARY_KEY, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );

        if ( !daoUtil.next( ) )
        {
            daoUtil.free( );

            return false;
        }

        daoUtil.free( );

        return true;
    }

    /**
     * Check whether a portlet is linked to a newsletter
     * 
     * @param nIdNewsletter the id of the newsletter
     * @return true if the newsletter is used by subscription portlet
     */
    public boolean checkLinkedPortlet( int nIdNewsletter )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_CHECK_LINKED_PORTLET );
        daoUtil.setInt( 1, nIdNewsletter );
        daoUtil.executeQuery( );

        if ( !daoUtil.next( ) )
        {
            daoUtil.free( );

            return false;
        }

        daoUtil.free( );

        return true;
    }

    /**
     * Generate a new primary key to add a newsletter
     * 
     * @param plugin the Plugin
     * @return the new key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PRIMARY_KEY, plugin );
        int nKey;

        daoUtil.executeQuery( );

        if ( !daoUtil.next( ) )
        {
            // If the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;

        daoUtil.free( );

        return nKey;
    }

    /**
     * Select the list of the newsletters available
     * @param plugin the Plugin
     * @return a collection of objects
     */
    public Collection<NewsLetter> selectAll( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL, plugin );
        daoUtil.executeQuery( );

        ArrayList<NewsLetter> list = new ArrayList<NewsLetter>( );

        while ( daoUtil.next( ) )
        {
            int nIndex = 1;
            NewsLetter newsLetter = new NewsLetter( );
            newsLetter.setId( daoUtil.getInt( nIndex++ ) );
            newsLetter.setName( daoUtil.getString( nIndex++ ) );
            newsLetter.setDescription( daoUtil.getString( nIndex++ ) );
            newsLetter.setDateLastSending( daoUtil.getTimestamp( nIndex++ ) );
            newsLetter.setHtml( daoUtil.getString( nIndex++ ) );
            newsLetter.setNewsLetterTemplateId( daoUtil.getInt( nIndex++ ) );
            newsLetter.setWorkgroup( daoUtil.getString( nIndex++ ) );
            newsLetter.setTestRecipients( daoUtil.getString( nIndex++ ) );
            newsLetter.setNewsletterSenderMail( daoUtil.getString( nIndex++ ) );
            newsLetter.setTestSubject( daoUtil.getString( nIndex++ ) );
            newsLetter.setNbCategories( daoUtil.getInt( nIndex ) );
            list.add( newsLetter );
        }

        daoUtil.free( );

        return list;
    }

    /**
     * Select the list of the newsletters available
     * @param plugin the Plugin
     * @return a {@link ReferenceList} of id and name
     */
    public ReferenceList selectAllId( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL_ID, plugin );
        daoUtil.executeQuery( );

        ReferenceList list = new ReferenceList( );

        while ( daoUtil.next( ) )
        {
            list.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free( );

        return list;
    }

    /**
     * Insert a new subscriber for a newsletter
     * 
     * @param nNewsLetterId the newsletter identifier
     * @param nSubscriberId the subscriber identifier
     * @param tToday The day
     * @param plugin the Plugin
     */
    public void insertSubscriber( int nNewsLetterId, int nSubscriberId, Timestamp tToday, Plugin plugin )
    {
        insertSubscriber( nNewsLetterId, nSubscriberId, true, tToday, plugin );
    }

    /**
     * Insert a new subscriber for a newsletter
     * 
     * @param nNewsLetterId the newsletter identifier
     * @param nSubscriberId the subscriber identifier
     * @param bValidate the validation status
     * @param tToday The day
     * @param plugin the Plugin
     */
    public void insertSubscriber( int nNewsLetterId, int nSubscriberId, boolean bValidate, Timestamp tToday,
            Plugin plugin )
    {
        // Check if the subscriber is yet registered for the newsletter
        if ( isRegistered( nNewsLetterId, nSubscriberId, plugin ) )
        {
            return;
        }

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_SUBSCRIBER, plugin );

        daoUtil.setInt( 1, nNewsLetterId );
        daoUtil.setInt( 2, nSubscriberId );
        daoUtil.setTimestamp( 3, tToday );
        daoUtil.setBoolean( 4, bValidate );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * Remove the subscriber's inscription to a newsletter
     * 
     * @param nNewsLetterId the newsletter identifier
     * @param nSubscriberId the subscriber identifier
     * @param plugin the Plugin
     */
    public void deleteSubscriber( int nNewsLetterId, int nSubscriberId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_FROM_SUBSCRIBER, plugin );

        daoUtil.setInt( 1, nNewsLetterId );
        daoUtil.setInt( 2, nSubscriberId );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * Performs confirm unsubscription process
     * 
     * @param confirmLimitDate all unconfirmed subscriber which subscription
     *            date is below confirmLimitDate will be deleted
     * @param plugin the plugin
     */
    public void deleteOldUnconfirmed( Timestamp confirmLimitDate, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_OLD_FROM_SUBSCRIBER, plugin );
        daoUtil.setTimestamp( 1, confirmLimitDate );
        daoUtil.setBoolean( 2, false );

        daoUtil.executeUpdate( );
        daoUtil.free( );

        daoUtil = new DAOUtil( SQL_QUERY_DELETE_UNUSED_EMAIL, plugin );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * check if the subscriber is not yet registered to a newsletter
     * 
     * @param nNewsLetterId the newsletter identifier
     * @param nSubscriberId the subscriber identifier
     * @param plugin the Plugin
     * @return true if he is registered and false if not
     */
    public boolean isRegistered( int nNewsLetterId, int nSubscriberId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_CHECK_IS_REGISTERED, plugin );

        daoUtil.setInt( 1, nNewsLetterId );
        daoUtil.setInt( 2, nSubscriberId );
        daoUtil.executeQuery( );

        if ( !daoUtil.next( ) )
        {
            daoUtil.free( );

            return false;
        }

        daoUtil.free( );

        return true;
    }

    /**
     * controls that a template is used by a newsletter
     * 
     * @param nTemplateId the template identifier
     * @param plugin the Plugin
     * @return true if the template is used, false if not
     */
    public boolean isTemplateUsed( int nTemplateId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_CHECK_IS_TEMPLATE_USED, plugin );

        daoUtil.setInt( 1, nTemplateId );
        daoUtil.setInt( 2, nTemplateId );
        daoUtil.executeQuery( );

        if ( !daoUtil.next( ) )
        {
            daoUtil.free( );

            return false;
        }

        daoUtil.free( );

        return true;
    }

    /**
     * Counts the subscribers for a newsletter
     * 
     * @param nNewsLetterId the newsletter newsletter
     * @param strSearchString the string to search in the subscriber's email
     * @param plugin the Plugin
     * @return the number of subscribers
     */
    public int selectNbrSubscribers( int nNewsLetterId, String strSearchString, Plugin plugin )
    {
        int nCount;

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_NBR_SUBSCRIBERS, plugin );

        daoUtil.setString( 1, "%" + strSearchString + "%" );
        daoUtil.setInt( 2, nNewsLetterId );

        daoUtil.executeQuery( );

        if ( !daoUtil.next( ) )
        {
            // If the table is empty
            nCount = 0;
        }

        nCount = daoUtil.getInt( 1 );

        daoUtil.free( );

        return nCount;
    }

    /**
     * Counts the subscribers for a newsletter
     * 
     * @param nNewsLetterId the newsletter newsletter
     * @param strSearchString the string to search in the subscriber's email
     * @param plugin the Plugin
     * @return the number of subscribers
     */
    public int selectNbrActiveSubscribers( int nNewsLetterId, String strSearchString, Plugin plugin )
    {
        int nCount;

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_NBR_ACTIVE_SUBSCRIBERS, plugin );

        daoUtil.setString( 1, "%" + strSearchString + "%" );
        daoUtil.setInt( 2, nNewsLetterId );

        daoUtil.executeQuery( );

        if ( !daoUtil.next( ) )
        {
            // If the table is empty
            nCount = 0;
        }

        nCount = daoUtil.getInt( 1 );

        daoUtil.free( );

        return nCount;
    }

    /**
     * {@inheritDoc}
     */
    public void validateSubscriber( int nNewsLetterId, int nSubscriberId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_VALIDATE_SUBSCRIBER, plugin );

        daoUtil.setInt( 1, nNewsLetterId );
        daoUtil.setInt( 2, nSubscriberId );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }
}
