--
-- Dumping data for table core_admin_right
--

INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url) VALUES ('NEWSLETTER_MANAGEMENT','newsletter.adminFeature.newsletter_management.name',2,'jsp/admin/plugins/newsletter/ManageNewsLetter.jsp','newsletter.adminFeature.newsletter_management.description',0,'newsletter','CONTENT','ti ti-news', NULL);
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url) VALUES ('NEWSLETTER_TEMPLATE_MANAGEMENT','newsletter.adminFeature.newsletter_template_management.name',2,'jsp/admin/plugins/newsletter/ManageTemplates.jsp','newsletter.adminFeature.newsletter_template_management.description',0,'newsletter','STYLE','ti ti-template', NULL);

--
-- Dumping data for table core_admin_role
--

INSERT INTO core_admin_role VALUES ('newsletter_manager','The role needed to access a newsletter template');


--
-- Dumping data for table core_admin_role_resource
--

INSERT INTO core_admin_role_resource VALUES (109,'newsletter_manager','NEWSLETTER','*','*');
INSERT INTO core_admin_role_resource VALUES (110,'newsletter_manager','NEWSLETTER_TEMPLATE','*','*');

 
--
-- Dumping data for table core_portlet_type
--

INSERT INTO core_portlet_type VALUES ('NEWSLETTER_ARCHIVE_PORTLET','newsletter.portlet.name','plugins/newsletter/CreatePortletNewsletter.jsp','plugins/newsletter/ModifyPortletNewsletter.jsp','fr.paris.lutece.plugins.newsletter.business.portlet.NewsLetterArchivePortletHome','newsletter','plugins/newsletter/DoCreatePortletNewsletter.jsp','/admin/portlet/script_create_portlet.html','','','plugins/newsletter/DoModifyPortletNewsletter.jsp','/admin/portlet/script_modify_portlet.html','/admin/plugins/newsletter/newsletter_sending_list.html','','archive');
INSERT INTO core_portlet_type VALUES ('NEWSLETTER_SUBSCRIPTION_PORTLET','newsletter.portlet.subscription.name','plugins/newsletter/CreateSubscriptionPortletNewsletter.jsp','plugins/newsletter/ModifySubscriptionPortletNewsletter.jsp','fr.paris.lutece.plugins.newsletter.business.portlet.NewsLetterSubscriptionPortletHome','newsletter','plugins/newsletter/DoCreateSubscriptionPortletNewsletter.jsp','/admin/portlet/script_create_portlet.html','','','plugins/newsletter/DoModifySubscriptionPortletNewsletter.jsp','/admin/portlet/script_modify_portlet.html','/admin/plugins/newsletter/newsletter_subscription_list.html','','news');

--
-- Dumping data for table core_style
--

INSERT INTO core_style VALUES (1100,'Newsletter-Archives','NEWSLETTER_ARCHIVE_PORTLET',0);
INSERT INTO core_style VALUES (1101,'Newsletter-Subscription','NEWSLETTER_SUBSCRIPTION_PORTLET',0);

--
-- Dumping data for table core_style_mode_stylesheet
--

INSERT INTO core_style_mode_stylesheet VALUES (1100,0,400);
INSERT INTO core_style_mode_stylesheet VALUES (1101,0,401);

--
-- Dumping data for table core_stylesheet
--

INSERT INTO core_stylesheet VALUES (400,'Rubrique Newsletter - Archives','portlet_newsletter_archive.xsl',0x3C3F786D6C2076657273696F6E3D22312E30223F3E0A3C78736C3A7374796C6573686565742076657273696F6E3D22312E302220786D6C6E733A78736C3D22687474703A2F2F7777772E77332E6F72672F313939392F58534C2F5472616E73666F726D223E0A202020203C78736C3A706172616D206E616D653D22736974652D70617468222073656C6563743D22736974652D7061746822202F3E0A202020203C78736C3A7661726961626C65206E616D653D22706F72746C65742D6964222073656C6563743D22706F72746C65742F706F72746C65742D696422202F3E0A202020200A202020203C78736C3A74656D706C617465206D617463683D22706F72746C6574223E0A20202020202020203C78736C3A7661726961626C65206E616D653D226465766963655F636C617373223E0A2020202020202020202020203C78736C3A63686F6F73653E0A202020202020202020202020202020203C78736C3A7768656E20746573743D22737472696E6728646973706C61792D6F6E2D736D616C6C2D646576696365293D273027223E686964652D666F722D736D616C6C3C2F78736C3A7768656E3E0A202020202020202020202020202020203C78736C3A6F74686572776973653E3C2F78736C3A6F74686572776973653E0A2020202020202020202020203C2F78736C3A63686F6F73653E0A20202020202020203C2F78736C3A7661726961626C653E0A20202020202020203C64697620636C6173733D22706F72746C6574207B246465766963655F636C6173737D223E0A2020202020202020202020203C78736C3A696620746573743D226E6F7428737472696E6728646973706C61792D706F72746C65742D7469746C65293D27312729223E0A202020202020202020202020202020203C683320636C6173733D22706F72746C65742D686561646572223E0A20202020202020202020202020202020202020203C78736C3A76616C75652D6F662064697361626C652D6F75747075742D6573636170696E673D22796573222073656C6563743D22706F72746C65742D6E616D6522202F3E0A202020202020202020202020202020203C2F68333E0A2020202020202020202020203C2F78736C3A69663E0A2020202020202020202020203C64697620636C6173733D22706F72746C65742D636F6E74656E74223E0A202020202020202020202020202020203C78736C3A6170706C792D74656D706C617465732073656C6563743D226E6577736C65747465722D73656E64696E672D6C69737422202F3E0A2020202020202020202020203C2F6469763E0A20202020202020203C2F6469763E0A202020203C2F78736C3A74656D706C6174653E0A0A0A202020203C78736C3A74656D706C617465206D617463683D226E6577736C65747465722D73656E64696E672D6C697374223E0A20202020202020203C756C20636C6173733D22756E7374796C6564223E0A2020202020202020202020203C78736C3A6170706C792D74656D706C617465732073656C6563743D226E6577736C65747465722D73656E64696E6722202F3E0A20202020202020203C2F756C3E0A202020203C2F78736C3A74656D706C6174653E0A0A0A202020203C78736C3A74656D706C617465206D617463683D226E6577736C65747465722D73656E64696E67223E0A20202020202020203C6C693E0A2020202020202020202020203C7370616E20636C6173733D226C6162656C206C6162656C2D696E666F223E0A202020202020202020202020202020203C78736C3A76616C75652D6F662073656C6563743D226E6577736C65747465722D73656E64696E672D6461746522202F3E0A2020202020202020202020203C2F7370616E3E0A2020202020202020202020203C6120687265663D226A73702F736974652F706C7567696E732F6E6577736C65747465722F566965774E6577736C6574746572417263686976652E6A73703F73656E64696E675F69643D7B6E6577736C65747465722D73656E64696E672D69647D22207461726765743D225F626C616E6B223E0A2020202020202020202020202020203C7374726F6E673E0A20202020202020202020202020202020202020203C78736C3A76616C75652D6F662073656C6563743D226E6577736C65747465722D73656E64696E672D7375626A65637422202F3E0A2020202020202020202020202020203C2F7374726F6E673E0A2020202020202020202020203C2F613E200A20202020202020203C2F6C693E0A202020203C2F78736C3A74656D706C6174653E0A0A3C2F78736C3A7374796C6573686565743E0A0A);
INSERT INTO core_stylesheet VALUES (401,'Rubrique Newsletter - Souscription','portlet_newsletter_subscription.xsl',0x3C3F786D6C2076657273696F6E3D22312E3022203F3E0D0A3C78736C3A7374796C6573686565742076657273696F6E3D22312E302220786D6C6E733A78736C3D22687474703A2F2F7777772E77332E6F72672F313939392F58534C2F5472616E73666F726D223E0D0A202020203C78736C3A6F7574707574206D6574686F643D2268746D6C2220696E64656E743D22796573222F3E0D0A202020203C78736C3A706172616D206E616D653D22736974652D70617468222073656C6563743D22736974652D7061746822202F3E0D0A202020203C78736C3A7661726961626C65206E616D653D22706F72746C65742D6964222073656C6563743D22706F72746C65742F706F72746C65742D696422202F3E0D0A202020203C78736C3A7661726961626C65206E616D653D22652D6D61696C2D6572726F72222073656C6563743D22706F72746C65742F6E6577736C65747465722D656D61696C2D6572726F7222202F3E0D0A202020203C78736C3A7661726961626C65206E616D653D226E6F63686F6963652D6572726F72222073656C6563743D22706F72746C65742F737562736372697074696F6E2D6E6F63686F6963652D6572726F7222202F3E0D0A202020203C78736C3A74656D706C617465206D617463683D22706F72746C6574223E0D0A20202020202020203C78736C3A7661726961626C65206E616D653D226465766963655F636C617373223E0D0A2020202020202020202020203C78736C3A63686F6F73653E0D0A202020202020202020202020202020203C78736C3A7768656E20746573743D22737472696E6728646973706C61792D6F6E2D736D616C6C2D646576696365293D273027223E686964652D666F722D736D616C6C3C2F78736C3A7768656E3E0D0A202020202020202020202020202020203C78736C3A6F74686572776973653E3C2F78736C3A6F74686572776973653E0D0A2020202020202020202020203C2F78736C3A63686F6F73653E0D0A20202020202020203C2F78736C3A7661726961626C653E0D0A20202020202020203C64697620636C6173733D22706F72746C6574207B246465766963655F636C6173737D223E0D0A2020202020202020202020203C78736C3A696620746573743D226E6F7428737472696E6728646973706C61792D706F72746C65742D7469746C65293D27312729223E0D0A202020202020202020202020202020203C683320636C6173733D22706F72746C65742D686561646572223E0D0A20202020202020202020202020202020202020203C78736C3A76616C75652D6F662064697361626C652D6F75747075742D6573636170696E673D22796573222073656C6563743D22706F72746C65742D6E616D6522202F3E0D0A202020202020202020202020202020203C2F68333E0D0A2020202020202020202020203C2F78736C3A69663E0D0A2020202020202020202020203C64697620636C6173733D22706F72746C65742D636F6E74656E74223E0D0A202020202020202020202020202020203C666F726D20636C6173733D22666F726D222069643D226E6577736C65747465722220616374696F6E3D227B24736974652D706174687D22206D6574686F643D22706F7374223E0D0A20202020202020202020202020202020202020203C696E707574206E616D653D2270616765222076616C75653D226E6577736C65747465722220747970653D2268696464656E22202F3E0D0A20202020202020202020202020202020202020203C696E707574206E616D653D22616374696F6E222076616C75653D2272656769737465722220747970653D2268696464656E22202F3E0D0A20202020202020202020202020202020202020203C696E707574206E616D653D22706C7567696E5F6E616D65222076616C75653D226E6577736C65747465722220747970653D2268696464656E22202F3E0D0A20202020202020202020202020202020202020203C6C6162656C20666F723D22656D61696C223E0D0A2020202020202020202020202020202020202020202020203C78736C3A76616C75652D6F662073656C6563743D226E6577736C65747465722D737562736372697074696F6E2D656D61696C22202F3E0D0A20202020202020202020202020202020202020203C2F6C6162656C3E0D0A20202020202020202020202020202020202020203C696E707574206E616D653D22656D61696C222069643D22656D61696C22206D61786C656E6774683D223130302220747970653D22746578742220636C6173733D22696E7075742D6C6172676522202F3E0D0A20202020202020202020202020202020202020203C78736C3A6170706C792D74656D706C617465732073656C6563743D226E6577736C65747465722D737562736372697074696F6E2D6C69737422202F3E2020200D0A20202020202020202020202020202020202020203C78736C3A696620746573743D226E6F7428737472696E67286E6577736C65747465722D737562736372697074696F6E2D746F73293D272729223E0D0A2020202020202020202020202020202020202020202020203C6C6162656C20636C6173733D22636865636B626F782220666F723D22746F70223E0D0A202020202020202020202020202020202020202020202020202020203C696E70757420747970653D22636865636B626F7822206E616D653D22746F73222069643D22746F73222076616C75653D223122202F3E0D0A20202020202020202020202020202020202020202020202020202020236931386E7B6E6577736C65747465722E736974654D6573736167652E746F732E7469746C657D0D0A090909090909093C627574746F6E20636C6173733D2262746E2062746E2D6C696E6B2220747970653D22627574746F6E2220646174612D746F67676C653D226D6F64616C2220646174612D7461726765743D2223726571756972656D656E744D6F64616C223E20236931386E7B6E6577736C65747465722E706167655F6E6577736C65747465722E746F732E6865616465727D3C2F627574746F6E3E0D0A2020202020202020202020202020202020202020202020203C2F6C6162656C3E0D0A2020202020202020202020202020202020202020202020203C6469762069643D22726571756972656D656E744D6F64616C2220636C6173733D226D6F64616C20666164652220746162696E6465783D222D312220617269612D6C6162656C6C656462793D22726571756972656D656E744C6162656C2220617269612D68696464656E3D2274727565223E0D0A090909090909093C64697620636C6173733D226D6F64616C2D6469616C6F67223E0D0A09090909090909093C64697620636C6173733D226D6F64616C2D636F6E74656E74223E0D0A0909090909090909093C64697620636C6173733D226D6F64616C2D686561646572223E0D0A090909090909090909093C68322069643D22726571756972656D656E744C6162656C223E236931386E7B6E6577736C65747465722E706167655F6E6577736C65747465722E746F732E6865616465727D3C2F68323E0D0A090909090909090909093C627574746F6E20747970653D22627574746F6E2220636C6173733D22636C6F73652220646174612D6469736D6973733D226D6F64616C2220617269612D68696464656E3D2274727565223E583C2F627574746F6E3E0D0A0909090909090909093C2F6469763E0D0A0909090909090909093C64697620636C6173733D226D6F64616C2D626F647920702D33223E0D0A090909090909090909093C78736C3A76616C75652D6F662064697361626C652D6F75747075742D6573636170696E673D22796573222073656C6563743D226E6577736C65747465722D737562736372697074696F6E2D746F732D636F6E74656E7422202F3E0D0A0909090909090909093C2F6469763E0D0A0909090909090909093C64697620636C6173733D226D6F64616C2D666F6F746572223E0D0A090909090909090909093C627574746F6E20636C6173733D2262746E2220646174612D6469736D6973733D226D6F64616C2220617269612D68696464656E3D2274727565223E236931386E7B706F7274616C2E7574696C2E6C6162656C4261636B7D3C2F627574746F6E3E0D0A0909090909090909093C2F6469763E0D0A09090909090909093C2F6469763E0D0A090909090909093C2F6469763E0D0A2020202020202020202020202020202020202020202020203C2F6469763E0D0A20202020202020202020202020202020202020203C2F78736C3A69663E090909090909090D0A20202020202020202020202020202020202020203C78736C3A696620746573743D226E6F7428737472696E67286E6577736C65747465722D737562736372697074696F6E2D63617074636861293D272729223E0D0A2020202020202020202020202020202020202020202020203C78736C3A76616C75652D6F662064697361626C652D6F75747075742D6573636170696E673D22796573222073656C6563743D226E6577736C65747465722D737562736372697074696F6E2D6361707463686122202F3E0D0A20202020202020202020202020202020202020203C2F78736C3A69663E0909090909090D0A20202020202020202020202020202020202020203C627574746F6E20747970653D227375626D69742220636C6173733D2262746E2062746E2D7072696D617279223E0D0A2020202020202020202020202020202020202020202020203C6920636C6173733D2269636F6E2D706C75732069636F6E2D7768697465223E26233136303B3C2F693E26233136303B0D0A2020202020202020202020202020202020202020202020203C78736C3A76616C75652D6F662073656C6563743D226E6577736C65747465722D737562736372697074696F6E2D627574746F6E22202F3E0D0A20202020202020202020202020202020202020203C2F627574746F6E3E0D0A20202020202020202020202020202020202020203C696E707574206E616D653D22706F72746C65745F6964222076616C75653D227B706F72746C65742D69647D2220747970653D2268696464656E22202F3E0D0A202020202020202020202020202020203C2F666F726D3E0D0A2020202020202020202020203C2F6469763E0D0A20202020202020203C2F6469763E0D0A202020203C2F78736C3A74656D706C6174653E0D0A0D0A202020203C78736C3A74656D706C617465206D617463683D226E6577736C65747465722D737562736372697074696F6E2D6C697374223E0D0A20202020202020203C78736C3A696620746573743D226E6577736C65747465722D737562736372697074696F6E223E0D0A2020202020202020202020203C756C3E0D0A202020202020202020202020202020203C78736C3A6170706C792D74656D706C617465732073656C6563743D226E6577736C65747465722D737562736372697074696F6E22202F3E0D0A2020202020202020202020203C2F756C3E0D0A20202020202020203C2F78736C3A69663E0D0A202020203C2F78736C3A74656D706C6174653E0D0A0D0A202020203C78736C3A74656D706C617465206D617463683D226E6577736C65747465722D737562736372697074696F6E223E0D0A20202020202020203C6C693E0D0A2020202020202020202020203C6C6162656C20636C6173733D22636865636B626F782220666F723D226E6577736C65747465725F69645F7B6E6577736C65747465722D737562736372697074696F6E2D69647D223E0D0A202020202020202020202020202020203C696E70757420747970653D22636865636B626F782220636C6173733D22636865636B626F782D6669656C6422206E616D653D226E6577736C65747465725F6964222069643D226E6577736C65747465725F69645F7B6E6577736C65747465722D737562736372697074696F6E2D69647D222076616C75653D227B6E6577736C65747465722D737562736372697074696F6E2D69647D2220636865636B65643D22636865636B656422202F3E0D0A202020202020202020202020202020203C78736C3A76616C75652D6F662073656C6563743D226E6577736C65747465722D737562736372697074696F6E2D7375626A65637422202F3E0D0A2020202020202020202020203C2F6C6162656C3E0D0A20202020202020203C2F6C693E0D0A202020203C2F78736C3A74656D706C6174653E0D0A3C2F78736C3A7374796C6573686565743E);

--
-- Dumping data for table core_user_right
--

INSERT INTO core_user_right (id_right,id_user) VALUES ('NEWSLETTER_TEMPLATE_MANAGEMENT',1);
INSERT INTO core_user_right (id_right,id_user) VALUES ('NEWSLETTER_MANAGEMENT',1);
INSERT INTO core_user_right (id_right,id_user) VALUES ('NEWSLETTER_MANAGEMENT',2);

--
-- Dumping data for table core_user_role
--

INSERT INTO core_user_role VALUES ('newsletter_manager',1);
INSERT INTO core_user_role VALUES ('newsletter_manager',2);