Ext.define('Luntan.view.main.EconomyDocList', {
    extend: 'Luntan.view.main.BasicListGrid',
    requires: [
    ],
    xtype: 'edoclist',
	reference: 'edocList',

    title: 'Nådiga luntor',

	controller: 'edoclist',
	viewModel: 'edocmodel',
/*
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
 */

//	bind: {store: '{fundingmodels}'},
	store: 'EconomyDocStore',

//	features: [{ ftype: 'grouping',startCollapsed: true }],

    columns: [
		{xtype:'actioncolumn',
            width:100, align: 'center',
            items: [{
				iconCls: 'x-fa fa-list-alt',
				tooltip: 'Visa Luntan',
				handler: function(grid, rowIndex, colIndex){
					var rec = grid.getStore().getAt(rowIndex);
					window.open(Luntan.data.Constants.BASE_URL.concat('view/economydoc?year=').concat(rec.get('year')));
				}
            },{
				iconCls: 'x-fa fa-map-o',
				tooltip: 'Visa eventuellt supplement till Luntan',
				handler: function(grid, rowIndex, colIndex){
					var rec = grid.getStore().getAt(rowIndex);
					window.open(Luntan.data.Constants.BASE_URL.concat('view/supplementdoc?year=').concat(rec.get('year')));
				}
            }, {
				iconCls: 'x-fa fa-file-excel-o',
				tooltip: 'Exportera till Excel',
				handler: function(grid, rowIndex, colIndex){
					var rec = grid.getStore().getAt(rowIndex);
					window.open(Luntan.data.Constants.BASE_URL.concat('excel/economydoc?year=').concat(rec.get('year')));
				}
			}, {
				iconCls: 'x-fa fa-upload',
				tooltip: 'Importera antal registrerade studenter',
				handler: function(grid, rowIndex, colIndex){
					var rec = grid.getStore().getAt(rowIndex);
					window.open(Luntan.data.Constants.BASE_URL.concat('rest/bulk/cibycsv?year=').concat(rec.get('year')));
				}
			}, {
				iconCls: 'x-fa fa-pencil',
				tooltip: 'Exportera kurstillfällen till Excel',
				handler: function(grid, rowIndex, colIndex){
					var rec = grid.getStore().getAt(rowIndex);
					window.open(Luntan.data.Constants.BASE_URL.concat('excel/courseinstances?year=').concat(rec.get('year')));
				}
			}
/* 			,{
				iconCls: 'x-fa fa-user',
				tooltip: 'Lista examinatorer',
				handler: function(grid, rowIndex, colIndex){
					var rec = grid.getStore().getAt(rowIndex);
					window.open(Luntan.data.Constants.BASE_URL.concat('view/examiners?year=').concat(rec.get('year')));
				}
			}
 */			
 			]
        },
/* 
		{ xtype: 'checkcolumn', text: 'Klona', dataIndex: 'cloneCourses', editor: 'checkboxfield', editable: true, align: 'center', width: 70, filter: 'boolean'},
 */
		{ xtype: 'checkcolumn', text: 'Låst', dataIndex: 'locked', editor: 'checkboxfield', editable: true, align: 'center', width: 70, filter: 'boolean'},
/* 
		{ xtype: 'checkcolumn', text: 'Regs OK', dataIndex: 'registrationsValid', editor: 'checkboxfield', editable: true, align: 'center', width: 80, filter: 'boolean'},
 */
		{ text: 'År', dataIndex: 'year', editor: 'textfield', filter: 'number', align: 'left', width: 100},
		{ text: 'Antal kurser', dataIndex: 'numberOfCIs', filter: 'number', align: 'left', width: 100},
		{ text: 'Basnivå', dataIndex: 'baseValue', editor: 'textfield', filter: 'number', align: 'left', width: 100},
		{ text: 'TekNat Ämnesgranskare', dataIndex: 'readerBaseValue', editor: 'textfield', filter: 'number', align: 'left', width: 100, renderer: Ext.util.Format.numberRenderer('0 kr')},
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

