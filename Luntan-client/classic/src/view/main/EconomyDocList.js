Ext.define('Luntan.view.main.EconomyDocList', {
    extend: 'Luntan.view.main.BasicListGrid',
    requires: [
    ],
    xtype: 'edoclist',
	reference: 'edocList',

    title: 'Nådiga luntor',

	controller: 'edoclist',
//	viewModel: 'fundingmodel',
	viewModel: {
		data: {
			current : {
				edoc : null
			}
		},
		stores: {
			departments: 'DepartmentStore'
		},
		formulas: {
			currentEconomyDoc: {
				// We need to bind deep to be notified on each model change
				bind: {
					bindTo: '{edocList.selection}', //--> reference configurated on the grid view (reference: ouList)
					deep: true
				},
				get: function(edoc) {
					this.set('current.edoc', edoc);
					return edoc;
				},
			}
		}
	},

//	bind: {store: '{fundingmodels}'},
	store: 'EconomyDocStore',
		
//	features: [{ ftype: 'grouping',startCollapsed: true }],

    columns: [
		{ xtype: 'checkcolumn', text: 'Klona', dataIndex: 'cloneCourses', editor: 'checkboxfield', editable: true, align: 'center', width: 50, filter: 'boolean'},
		{ xtype: 'checkcolumn', text: 'Låst', dataIndex: 'locked', editor: 'checkboxfield', editable: true, align: 'center', width: 50, filter: 'boolean'},
		{ text: 'År', dataIndex: 'year', editor: 'textfield', filter: 'number', align: 'left', width: 100},
		{ text: 'Antal kurser', dataIndex: 'numberOfCIs', filter: 'number', align: 'left', width: 100},
		{ text: 'Basnivå', dataIndex: 'baseValue', editor: 'textfield', filter: 'number', align: 'left', width: 100},
		{ text: 'Aktuella institutioner', dataIndex: 'accountedDepts', align: 'left', flex: 1,
			editor: new Ext.form.field.Tag({
				typeAhead: true,
				triggerAction: 'all',
				bind: {store: '{departments}'},
				queryMode: 'local',
				lastQuery: '',
				displayField: 'label',
			    valueField: 'label'
				
			})
		},
		{ text: 'Senast ändrad av', dataIndex: 'lastModifiedBy', filter: 'string', align: 'left', width: 150 },
		{ text: 'Senast ändrad', dataIndex: 'lastModifiedDate', filter: 'date', align: 'left', width: 150 },
		{ text: 'Anteckningar', dataIndex: 'note', editor: 'textfield', filter: 'string', align: 'left', flex: 2 }

	],

	config : {
	}


});

