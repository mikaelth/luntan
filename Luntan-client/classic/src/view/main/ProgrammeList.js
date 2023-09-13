Ext.define('Luntan.view.main.ProgrammeList', {
    extend: 'Luntan.view.main.BasicListGrid',
    requires: [
    ],
    xtype: 'programmelist',
	reference: 'proglist',

    title: 'Utbildningsprogram',

	controller: 'programmelist',
//	viewModel: {type:'coursemodel'},


//	store: 'ProgrammeStore',

	bind: {
		store: '{programmes}',
		title: '<b>Utbildningsprogram</b>'
	},

	features: [{ ftype: 'grouping',startCollapsed: false }],

    columns: [
		{xtype:'actioncolumn',
            width:40,
            items: [{
				iconCls: 'x-fa fa-list-alt',
				tooltip: 'Studieplan',
				handler: function(grid, rowIndex, colIndex){
					var rec = grid.getStore().getAt(rowIndex);
//					window.open(Luntan.data.Constants.SELMA_P_URL.concat(rec.get('SELMAPath')));
					window.open(Luntan.data.Constants.SELMA_NEW_P_URL.concat(rec.get('linkId')));
				}
			}]
        },
		{ xtype: 'checkcolumn', text: 'Inaktiv', dataIndex: 'inactive', editor: 'checkboxfield', editable: true, filter: 'boolean', align: 'center', width: 50, filter: 'boolean'},
		{ text: 'Programkod', dataIndex: 'code', editor: 'textfield', filter: 'string', align: 'left', width: 100 },
		{ text: 'Länk ID', dataIndex: 'linkId', editor: 'textfield', filter: 'string', align: 'left', width: 100 },
		{ text: 'Benämning', dataIndex: 'seName', editor: 'textfield', filter: 'string', align: 'left', flex: 2},
		{ text: 'Ingång', dataIndex: 'direction', editor: 'textfield', filter: 'string', align: 'left', flex: 2},
		{ text: 'Programansvarig', dataIndex: 'programDirector', align: 'left', flex: 1,
		    renderer: function(value) {
				if (Ext.getStore('TeacherStore').getById(value) != undefined) {
					return Ext.getStore('TeacherStore').getById(value).get('name');
				} else {
					return value;
				}
        	},
			editor: {
				xtype: 'combobox',
				typeAhead: true,
				triggerAction: 'all',
				bind: {store: '{teachers}'},
				queryMode: 'local',
				lastQuery: '',
				displayField: 'name',
			    valueField: 'employeeNumber'
			}
		},
		{ text: 'Anteckningar', dataIndex: 'note', editor: 'textfield', filter: 'string', align: 'left', flex: 3 }

	],

	config : {
	}


});

