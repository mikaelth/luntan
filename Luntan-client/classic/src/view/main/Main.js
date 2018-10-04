/**
 * This class is the main view for the application. It is specified in app.js as the
 * "mainView" property. That setting automatically applies the "viewport"
 * plugin causing this view to become the body element (i.e., the viewport).
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('Luntan.view.main.Main', {
    extend: 'Ext.tab.Panel',
    xtype: 'app-main',

    requires: [
        'Ext.plugin.Viewport',
        'Ext.window.MessageBox',

        'Luntan.view.main.MainController',
        'Luntan.view.main.MainModel'
    ],

    controller: 'main',
    viewModel: 'main',

    ui: 'navigation',

    tabBarHeaderPosition: 1,
    titleRotation: 0,
    tabRotation: 0,

    header: {
        layout: {
            align: 'stretchmax'
        },
        title: {
            bind: {
                text: '{name}'
            },
            flex: 0
        },
        iconCls: 'fa-th-list'
    },

    tabBar: {
        flex: 1,
        layout: {
            align: 'stretch',
            overflowHandler: 'none'
        }
    },

    responsiveConfig: {
        tall: {
            headerPosition: 'top'
        },
        wide: {
            headerPosition: 'left'
        }
    },

    defaults: {
        bodyPadding: 20,
        tabConfig: {
            plugins: 'responsive',
            responsiveConfig: {
                wide: {
                    iconAlign: 'left',
                    textAlign: 'left'
                },
                tall: {
                    iconAlign: 'top',
                    textAlign: 'center',
                    width: 120
                }
            }
        }
    },

    items: [{
        title: 'Nådiga luntor',
        iconCls: 'fa-legal',
        // The following grid shares a store with the classic version's grid as well!
        items: [{
        	xtype: 'edoclist',
        	height: '900'
        }]
    },{
        title: 'Uppdrag',
        iconCls: 'fa-balance-scale',
        // The following grid shares a store with the classic version's grid as well!
        items: [{
        }]
    }, {
        title: 'Modeller',
        iconCls: 'fa-dollar',
        items: [{
            xtype: 'fmlist', 
			height: 600
        },{
            xtype: 'fmtabledlist', 
			height: 300
        }]
    }, {
        title: 'Kurstillfällen',
        iconCls: 'fa-pencil',
        items: [{
            xtype: 'cilist', 
			height: 900
        }]
    }, {
        title: 'Kurser',
        iconCls: 'fa-book',
        items: [{
            xtype: 'courselist', 
			height: 900
        }]
     }, {
        title: 'Användare',
        iconCls: 'fa-user',
        items: [{
            xtype: 'userlist', 
			height: 900
        }]
	}, {
        title: 'Gå till startsidan',
        reference: 'goBackTab',
        iconCls: ' fa-map'
    }, {
        title: 'Logga ut',
        reference: 'logOutTab',
        iconCls: 'fa-cog'
    }],
    
    listeners: {
 		beforeTabChange: 'onTabChange'
 	}


});
