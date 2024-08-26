Ext.define('Luntan.view.main.EDGrantList', {
    extend: 'Luntan.view.main.BasicListGrid',
    requires: [
    ],
    xtype: 'edgrantlist',
	reference: 'edGrantList',

    title: '<b>Gemansamma poster</b>',

	controller: 'edgrantlist',
	viewModel: 'edocmodel',



	bind: {
		store: '{edocgrants}',
		title: '<b>Gemansamma poster i luntan {current.edoc.year}</b>'
	},
		

    columns: [
		{ text: 'Post', dataIndex: 'itemDesignation', editor: 'textfield', filter: 'number', align: 'left', flex: 1},
		{ xtype: 'checkcolumn', text: 'Nyckel', dataIndex: 'usedForKey', editor: 'checkboxfield', editable: true, filter: 'boolean', align: 'center', width: 100},
		{ text: 'Typ av anslag', dataIndex: 'grantKind', align: 'left', width: 100,
         	renderer: function(value) {
				if (Ext.getStore('EDGKindStore').getById(value) != undefined) {
					return Ext.getStore('EDGKindStore').getById(value).get('displayname');
				} else {
					return value;
				}
        	},
			editor: {
				xtype: 'combobox',
				typeAhead: true,
				triggerAction: 'all',
				bind: {store: '{grantkinds}'},
				queryMode: 'local',
				lastQuery: '',
				displayField: 'displayname',
			    valueField: 'id'
			}
		},
		{ text: 'Totalt', dataIndex: 'totalGrant', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'number', align: 'left', width: 100},
		{ text: 'IBG', dataIndex: 'IBG', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'number', align: 'left', width: 80},
		{ text: 'ICM', dataIndex: 'ICM', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'number', align: 'left', width: 80 },
		{ text: 'IEG', dataIndex: 'IEG', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'number', align: 'left', width: 80 },
		{ text: 'IOB', dataIndex: 'IOB', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'number', align: 'left', width: 80},
		{ text: 'Kommentarer', dataIndex: 'note', editor: 'textfield', filter: 'string', align: 'left', flex: 2 }

	],

	config : {
	}


});

