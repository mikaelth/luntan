Ext.define('Luntan.view.main.CourseInstanceCloneList', {
    extend: 'Ext.grid.Panel',
    requires: [
    ],
    xtype: 'ciclonelist',
	reference: 'ciCloneList',

    title: 'Kurstillfällen, kloning',

	controller: 'courseinstanceclone',
	viewModel: {type:'coursemodel'},


	bind: {store: '{citaskstore}'},

	height: 600,

	frame: true,

    bbar: {
        items: [{
            xtype: 'component',
            itemId: 'order'
        }]
    },
	dockedItems: [{
		xtype: 'toolbar',
		items: [
		 			{
						text: 'Hämta alla',
						reference: 'btnReload',
						disabled: false,
						 listeners: {
							click: 'onReload'
						}
					}, {

						xtype: 'combobox',
						reference: 'comboCurrentYear',
						bind: {value: '{workingEDoc}', store: '{usedYears}'},
						width: 200,
						typeAhead: true,
						triggerAction: 'all',
						queryMode: 'local',
						lastQuery: '',
						fieldLabel: 'Välj år',
						labelWidth: 50,
						displayField: 'year',
						valueField: 'id',
						listeners: {
							select: 'onNewFromYear'
						}
					}, {

						xtype: 'combobox',
						reference: 'comboFutureYears',
						bind: {value: '{cloneDestEDoc}', store: '{futureYears}'},
						width: 300,
						typeAhead: true,
						triggerAction: 'all',
						queryMode: 'local',
						lastQuery: '',
						fieldLabel: 'Välj år att klona till',
						labelWidth: 150,
						displayField: 'year',
						valueField: 'id',
						listeners: {
							select: function() {
								this.lookupReferenceHolder().lookupReference('btnSave').enable();
							}
						}
					},
					'->', {
						text: 'Klona i db',
						reference: 'btnSave',
						bind:{disabled: '{!cloning.cloneDestED}'},
						listeners: {
							click: 'onItemsClone'
						}
					}, {
						text: 'Tag bort poster',
						reference: 'btnRemove',
						bind:{disabled: '{current.edoc.locked}'},
						disabled: true,
						 listeners: {
							click: 'onRemove'
						}
					}

			]
	}],
	selModel: {
		type: 'checkboxmodel',
		checkOnly: true,
		listeners: {
			selectionchange: 'onSelectionChange'
		}
	},
    plugins: ['gridfilters'],
	
//	features: [{ ftype: 'grouping',startCollapsed: false }],

    columns: [
		{ text: 'Kursgrupp', dataIndex: 'courseGroup', align: 'left', filter: 'string', width: 150 },
		{ text: 'Kurs', dataIndex: 'courseDesignation', align: 'left', filter: 'string', flex: 2,         	
// 			renderer: function(value,record) {
// 				if (Ext.getStore('CIDesignationStore').getById(record.record.get('extraDesignation')) != undefined) {
// 					return value.concat(Ext.getStore('CIDesignationStore').getById(record.record.get('extraDesignation')).get('displayname'));
// 				} else {
// 					return value;
// 				}
//         	},
		},
		{ text: 'Extra benämning', dataIndex: 'extraDesignation', filter: 'string', align: 'left', flex: 1, 
         	renderer: function(value) {
				if (Ext.getStore('CIDesignationStore').getById(value) != undefined) {
					return Ext.getStore('CIDesignationStore').getById(value).get('displayname');
				} else {
					return value;
				}
        	}
		},
		{ text: 'IBG', dataIndex: 'IBG', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'number', align: 'left', width: 70},
		{ text: 'ICM', dataIndex: 'ICM', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'number', align: 'left', width: 70 },
		{ text: 'IEG', dataIndex: 'IEG', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'number', align: 'left', width: 70 },
		{ text: 'IOB', dataIndex: 'IOB', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'number', align: 'left', width: 70},
		{ text: 'Betalningsmodell', dataIndex: 'fundingModelId', align: 'left', flex:1,
         	renderer: function(value) {
				if (Ext.getStore('FundingModelStore').getById(value) != undefined) {
					return Ext.getStore('FundingModelStore').getById(value).get('designation');
				} else {
					return value;
				}
        	},
		},
		{ text: 'Kommentarer', dataIndex: 'note', editor: 'textfield', filter: 'string', align: 'left', flex: 3 }

	],

	listeners: {

	},
	config : {
	}


});

