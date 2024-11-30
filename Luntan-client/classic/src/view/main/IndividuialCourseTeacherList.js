Ext.define('Luntan.view.main.IndividuialCourseTeacherList', {
    extend: 'Luntan.view.main.BasicListGrid',
    requires: [
    ],
    xtype: 'ictlist',
	reference: 'ictlist',

    title: '<b>Personer inblandade i kurstillfället</b>',

	controller: 'ictlist',

    clearSort:function(){
        // custom method
        var grid = this,
            store = grid.getStore();

        store.sorters.clear();
        grid.view.refresh();

        grid.refreshRank();

        grid.clearSortHappened = true;
    },
/*
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

	listeners: {
		storechange: function (view) {
			console.log("storechange", view);
			var store = view.getStore().hasOwnProperty('source') ? view.getStore().getSource() : view.getStore();
			store.on({'update': this.controller.onStoreContentUpdated, scope: this});
			store.on({'remove': this.controller.onStoreContentRemoved, scope: this});

		}
	},
	bind: {
		store: '{icts}',
		title: '<b>Personer inblandade i kurstillfället</b>'
//		parentReg: '{reg}'
	},


    columns: [
		{ text: 'Roll', dataIndex: 'teacherType', filter: 'list', align: 'left', width: 150,
         	renderer: function(value) {
				if (Ext.getStore('ICTKindStore').getById(value) != undefined) {
					return Ext.getStore('ICTKindStore').getById(value).get('displayname');
				} else {
					return value;
				}
        	},
			editor: {
				xtype: 'combobox',
				typeAhead: true,
				triggerAction: 'all',
				bind: {store: '{ictKinds}'},
				queryMode: 'local',
				lastQuery: '',
				displayField: 'displayname',
			    valueField: 'id',
   			    listeners: {change: 'onNewTeacherKind'}

			}
		},
		{ xtype: 'checkcolumn', text: 'Extern', dataIndex: 'external', editor: 'checkboxfield', editable: true, 
			listeners: {checkchange: 'onCheckChange'}, filter: 'boolean', align: 'center', width: 80, filter: 'boolean'},
//		{ text: 'LDAP', dataIndex: 'ldapEntry', align: 'left', width: 100},
		{ text: 'Lärare', dataIndex: 'ldapEntry', align: 'left', flex: 1,
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
			    listeners: {change: 'onNewTeacher'}

			}
		},
		{ text: 'Namn', dataIndex: 'name', editor: 'textfield', align: 'left', flex: 1},
		{ text: 'Institution och program', dataIndex: 'fullDepartment', editor: 'textfield', align: 'left', flex: 1, reference:'fullDeptField'},
		{ text: 'e-post', dataIndex: 'email', editor: 'textfield', align: 'left', width: 250},
		{ text: 'Telefon', dataIndex: 'phone', editor: 'textfield', align: 'left', width: 150},
		{ text: 'Kommentar', dataIndex: 'note', editor: 'textfield', filter: 'string', align: 'left', flex: 2 }

	],

	config : {

		parentCourse: null
	}


});

