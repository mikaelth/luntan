Ext.define('Luntan.view.main.ExaminerList', {
    extend: 'Luntan.view.main.BasicListGrid',
    requires: [
    ],
    xtype: 'examinerlist',
	reference: 'examinerList',

    title: '<b>Examinatorer</b>',

	controller: 'examinerlist',
//	viewModel: {type:'coursemodel'},
//	viewModel: 'examinermodel',

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

	bind: {
		store: '{examiners}',
//		title: '<b>Examinatorer {current.edoc.year}</b>'
		title: '<b>Examinatorer</b>'
	},
		

    columns: [
		{ text: 'Rang', dataIndex: 'rank', filter: 'number', align: 'left', width: 100},
		{ text: 'LDAP', dataIndex: 'ldapEntry', align: 'left', width: 100},
		{ text: 'Examinator', dataIndex: 'ldapEntry', filter: 'string', align: 'left', flex: 1,
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

