Ext.define('Luntan.view.main.UserList', {
    extend: 'Luntan.view.main.BasicListGrid',
    requires: [
    ],
    xtype: 'userlist',
	reference: 'userlist',

    title: 'Användare',

	controller: 'userlist',
	viewModel: 'user',


	store: 'UserStore',
	
	features: [{ ftype: 'grouping',startCollapsed: true }],

    columns: [
		{ text: 'AKKA-id', dataIndex: 'username', editor: 'textfield', filter: 'string', align: 'left', width: 150 },
		{ text: 'Förnamn', dataIndex: 'firstName', editor: 'textfield', filter: 'string', align: 'left', width: 100, flex: 1},
		{ text: 'Efternamn', dataIndex: 'lastName', editor: 'textfield', filter: 'string', align: 'left', flex: 1 },
		{ text: 'Behörigheter', dataIndex: 'userRoles', align: 'left', filter: 'list', flex: 1,
			editor: new Ext.form.field.Tag({
				typeAhead: true,
				triggerAction: 'all',
				bind: {store: '{usertypes}'},
				queryMode: 'local',
				lastQuery: '',
				displayField: 'label',
			    valueField: 'label'
				
			})},
		{ text: 'E-post', dataIndex: 'email', editor: 'textfield', filter: 'string', align: 'left', width: 200 },
		{ text: 'Anteckningar', dataIndex: 'note', editor: 'textfield', filter: 'string', align: 'left', flex: 2 }

	],

	config : {
	}


});

