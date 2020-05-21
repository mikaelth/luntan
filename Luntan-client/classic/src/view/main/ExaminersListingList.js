Ext.define('Luntan.view.main.ExaminersListingList', {
    extend: 'Luntan.view.main.BasicListGrid',
    requires: [
    ],
    xtype: 'examinerslisting',
	reference: 'examinerslisting',

    title: '<b>Beslut om examinatorer</b>',

	controller: 'examinerslisting',
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
		store: '{examinerslistings}',
//		title: '<b>Examinatorer {current.edoc.year}</b>'
		title: '<b>Beslut om examinatorer</b>'
	},
		

    columns: [
/* 
		{ xtype: 'checkcolumn', text: 'Beslut', dataIndex: 'decided', editor: 'checkboxfield', editable: true, align: 'center', width: 50, filter: 'boolean'},
 */
		{ text: 'Nämnd', dataIndex: 'board', filter: 'string', align: 'left', flex: 1,
		    renderer: function(value) {
				if (Ext.getStore('EduBoardStore').getById(value) != undefined) {
					return Ext.getStore('EduBoardStore').getById(value).get('displayname');
				} else {
					return value;
				}
        	},
			editor: {
				xtype: 'combobox',
				typeAhead: true,
				triggerAction: 'all',
				bind: {store: '{availBoards}'},
				queryMode: 'local',
				lastQuery: '',
				displayField: 'displayname',
			    valueField: 'id',
			}
		},
/* 
		{ text: 'Datum', dataIndex: 'decisionDate', filter: 'date', align: 'left', width: 150},
 */
		{ xtype: 'datecolumn',text: 'Beslutsdatum', dataIndex: 'decisionDate', format:'Y-m-d', editor: 'datefield', filter: 'date', align: 'left', width: 150},
		{ text: 'Examinatorer', dataIndex: 'defaultExaminers', align: 'left', filter: 'list', flex: 1,
			renderer: function(value, p, r) { 
				if (value.length > 0) { 
						var list = []; 
						for (i = 0; i < value.length; i++) { 
							list.push(' ' + Ext.getStore('TeacherStore').getById(value[i]).get('name')) 
						} 
						return list; 
					} 
			},
			editor: new Ext.form.field.Tag({
				typeAhead: true,
				triggerAction: 'all',
				bind: {store: '{teachers}'},
				queryMode: 'local',
				lastQuery: '',
				displayField: 'name',
			    valueField: 'employeeNumber'
				
			})},
		{ text: 'Kommentar', dataIndex: 'note', editor: 'textfield', filter: 'string', align: 'left', flex: 2 }

	],

	config : {
	}


});

