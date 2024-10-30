Ext.define('Luntan.view.main.IndividualCourseRegList', {
    extend: 'Luntan.view.main.BasicYearListGrid',
    requires: [
    ],
    xtype: 'coursereglist',
	reference: 'coursereglist',

    title: 'Individuella kurser, registreringar',

	controller: 'coursereglist',


	bind: {
		store: '{registrations}',
		title: '<b>Individuella kurser, registreringar</b>'
	},
	
	features: [{ ftype: 'grouping',startCollapsed: false }],

    columns: [
/* 
		{xtype:'actioncolumn',
            width:40,
            items: [{
				iconCls: 'x-fa fa-list-alt',
				tooltip: 'Kursplan',
				handler: function(grid, rowIndex, colIndex, item, e, record, row){
					window.open(Luntan.data.Constants.SELMA_URL.concat(record.get('code')));
				}
			}]
        },
		{ text: 'Kurskod', dataIndex: 'code', editor: 'textfield', filter: 'string', align: 'left', width: 100 },
		{ text: 'Benämning', dataIndex: 'seName', editor: 'textfield', filter: 'string', align: 'left', flex: 2},
//		{ text: 'Engelsk benämning', dataIndex: 'enName', editor: 'textfield', filter: 'string', align: 'left', flex: 1 },
		{ text: 'Poäng', dataIndex: 'credits', editor: 'textfield', align: 'left', width: 80 },
		{ text: 'Kursgrupp', dataIndex: 'courseGroup', filter: 'list', align: 'left', flex: 1,
         	renderer: function(value) {
				if (Ext.getStore('CourseGroupStore').getById(value) != undefined) {
					return Ext.getStore('CourseGroupStore').getById(value).get('displayname');
				} else {
					return value;
				}
        	},
			editor: {
				xtype: 'combobox',
				typeAhead: true,
				triggerAction: 'all',
				bind: {store: '{coursegroups}'},
				queryMode: 'local',
				lastQuery: '',
				displayField: 'displayname',
			    valueField: 'id'
			}
		},

//		{ xtype: 'checkcolumn', text: '- Ex', dataIndex: 'noExaminer', editable: false, filter: 'boolean', align: 'center', width: 50, filter: 'boolean'},

		{ text: 'Examinatoruppdatering', dataIndex: 'examinerDepartment', filter: 'list', align: 'left', flex: 1,
         	renderer: function(value) {
				if (Ext.getStore('DepartmentStore').getById(value) != undefined) {
					return Ext.getStore('DepartmentStore').getById(value).get('label');
				} else {
					return value;
				}
        	},
			editor: {
				xtype: 'combobox',
				typeAhead: true,
				triggerAction: 'all',
				bind: {store: '{depts}'},
				queryMode: 'local',
				lastQuery: '',
				displayField: 'label',
			    valueField: 'label'
			}
		},
		{ text: 'Examinatorsbeslut', dataIndex: 'board', filter: 'list', align: 'left', flex: 1,
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
			    valueField: 'id'
			}
		},
//		{ text: 'Kursperiod', dataIndex: 'period', editor: 'textfield', align: 'left', width: 80 },
 */

		{ xtype: 'checkcolumn', text: 'Klar', dataIndex: 'studentDone', editor: 'checkboxfield', editable: true, filter: 'boolean', align: 'center', width: 80, filter: 'boolean'},
		{ xtype: 'checkcolumn', text: 'KURT', dataIndex: 'courseEvalSetUp', editor: 'checkboxfield', editable: true, filter: 'boolean', align: 'center', width: 80, filter: 'boolean'},
		{ xtype: 'datecolumn',text: 'Registreriingsdatum', dataIndex: 'registrationDate', format:'Y-m-d', filter: 'date', align: 'left', width: 150},
		{ xtype: 'datecolumn',text: 'Startdatum', dataIndex: 'startDate', format:'Y-m-d', editor: 'datefield', filter: 'date', align: 'left', width: 150},
		{ text: 'Kurs', dataIndex: 'courseInstanceId', filter: 'list', align: 'left', flex: 1,
         	renderer: function(value) {
				if (Ext.getStore('CourseInstanceStore').getById(value) != undefined) {
					return Ext.getStore('CourseInstanceStore').getById(value).get('courseDesignation');
				} else {
					return value;
				}
        	},
			editor: {
				xtype: 'combobox',
				typeAhead: true,
				triggerAction: 'all',
				bind: {store: '{icis}'},
				queryMode: 'local',
				lastQuery: '',
				displayField: 'courseDesignation',
			    valueField: 'id'
			}
		},		
		{ text: 'Student', dataIndex: 'studentName', editor: 'textfield', filter: 'string', align: 'left', flex: 2},
		{ text: 'Reg inst.', dataIndex: 'regDepartment', editor: 'textfield', filter: 'string', align: 'left', flex: 2},
		{ text: 'Anteckningar', dataIndex: 'note', editor: 'textfield', filter: 'string', align: 'left', flex: 3 }

	],

	config : {
	}


});

