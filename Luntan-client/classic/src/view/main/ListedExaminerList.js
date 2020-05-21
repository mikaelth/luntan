Ext.define('Luntan.view.main.ListedExaminerList', {
    extend: 'Luntan.view.main.BasicListGrid',
    requires: [
    ],
    xtype: 'listedexaminerlist',
	reference: 'listedExaminerList',

    title: '<b>Examinatorer</b>',

	controller: 'listedexaminerlist',
//	viewModel: {type:'coursemodel'},
//	viewModel: 'examinermodel',

/* 
    refreshRank:function(){
        // custom method
        this.getStore().each(function(rec,ind){
            rec.set('rank',ind+1);
        });
    },
    clearSort:function(){
        // custom method
        var grid = this,
            store = grid.getStore();
            
        store.sorters.clear();
        grid.view.refresh();
        
        grid.refreshRank();
        
        grid.clearSortHappened = true;
    },
    viewConfig:{
        plugins:{
            ptype:'gridviewdragdrop'
        },
        listeners:{
            'drop':{
                fn:function(){
                    this.grid.ownerGrid.refreshRank();
                    
                    if( this.grid.ownerGrid.clearSortHappened ){
                       	this.grid.ownerGrid.setTitle('I\'m dropping record to be last instead');
                    }
                }
            },
            'afterrender':{
                delay:100,
                fn:function(){
                    this.grid.ownerGrid.refreshRank();
                }
            }
        }
    },
 */

	bind: {
		store: '{listedexaminers}',
		title: '<b>Examinatorer</b>'
	},
		
/* 
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
					}, 
		 			{
						text: 'Skriv ut',
						reference: 'btnPrint',
						disabled: false,
						handler: function() {
							Ext.ux.grid.Printer.printAutomatically = false;
							Ext.ux.grid.Printer.closeAutomaticallyAfterPrint = false;
							Ext.ux.grid.Printer.print(this.up('grid'));   
						}
					}, 
					'->', {
// 					text: 'Remove',
// 						text: 'Tag bort post',
// 						reference: 'btnRemove',
// 						disabled: true,
// 						 listeners: {
// 							click: 'onRemove'
// 						}
// 					}, {
// 					text: 'Create',
// 						text: 'Ny post',
// 						reference: 'btnCreate',
// 						listeners: {
// 							click: 'onCreate'
// 						}
// 					}, 
// 					{
						text: 'Spara till db',
						reference: 'btnSave',
						listeners: {
							click: 'onSave'
						}
					}

			]
	}],
 */

    columns: [
		{ text: 'Kurs', dataIndex: 'courseId', align: 'left', flex: 1,
			renderer: function(value) {
				if (Ext.getStore('CourseStore').getById(value) != undefined) {
					return Ext.getStore('CourseStore').getById(value).get('formName');
				} else {
					return value;
				}
        	}
		},
		{ text: 'Rang', dataIndex: 'rank', filter: 'number', align: 'left', width: 100},
		{ text: 'Examinator', dataIndex: 'ldapEntry', align: 'left', flex: 1,
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
			    valueField: 'employeeNumber',
			    listeners: {
			    	focus: function(qPlan, eOpts) {
			    		console.log(qPlan);
			    	}
			    }
			}
		},
		{ text: 'Program', dataIndex: 'ldapEntry', align: 'left', flex: 1,
			renderer: function(value) {
				if (Ext.getStore('TeacherStore').getById(value) != undefined) {
					return Ext.getStore('TeacherStore').getById(value).get('department');
				} else {
					return value;
				}
        	}
		},
		{ text: 'Tjänst', dataIndex: 'ldapEntry', align: 'left', flex: 1,
			renderer: function(value) {
				if (Ext.getStore('TeacherStore').getById(value) != undefined) {
					return Ext.getStore('TeacherStore').getById(value).get('title');
				} else {
					return value;
				}
        	}
		},
		{ text: 'e-post', dataIndex: 'ldapEntry', align: 'left', width: 250,
			renderer: function(value) {
				if (Ext.getStore('TeacherStore').getById(value) != undefined) {
					return Ext.getStore('TeacherStore').getById(value).get('mail');
				} else {
					return value;
				}
        	}
		},
		{ text: 'Telefon', dataIndex: 'ldapEntry', align: 'left', width: 150,
			renderer: function(value) {
				if (Ext.getStore('TeacherStore').getById(value) != undefined) {
					return Ext.getStore('TeacherStore').getById(value).get('phone');
				} else {
					return value;
				}
        	}
		},
		{ text: 'Kommentar', dataIndex: 'note', editor: 'textfield', filter: 'string', align: 'left', flex: 2 }

	],

	config : {
	}


});

