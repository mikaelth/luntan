Ext.define('Luntan.view.main.CredBaseCourseRegList', {
    extend: 'Luntan.view.main.BasicListGrid',
    requires: [
    ],
    xtype: 'credbasereglist',
	reference: 'credbasereglist',

    title: 'Inkluderade registreringar',

	controller: 'credbasereglist',


	bind: {
		store: '{regsincreds}',
		title: '<b>Inkluderade egistreringar</b>'
	},

	features: [{ ftype: 'grouping',startCollapsed: false }],

//    columns:  [{ xtype: 'IndividualCourseRegColumns'}],

	columns:
    [

		{ xtype: 'checkcolumn', text: 'Klar', dataIndex: 'studentDone', editor: 'checkboxfield', editable: false, filter: 'boolean', align: 'center', width: 80, filter: 'boolean'},
		{ xtype: 'checkcolumn', text: 'KURT', dataIndex: 'courseEvalSetUp', editor: 'checkboxfield', editable: false, filter: 'boolean', align: 'center', width: 80, filter: 'boolean'},
		{ xtype: 'datecolumn',text: 'Registreriingsdatum', dataIndex: 'registrationDate', format:'Y-m-d', editable: false, filter: 'date', align: 'left', width: 120},
		{ xtype: 'datecolumn',text: 'Startdatum', dataIndex: 'startDate', format:'Y-m-d', editable: false, filter: 'date', align: 'left', width: 120},
		{ text: 'År', dataIndex: 'economyDocId', editable: false, filter: 'number', align: 'left', width: 100,
         	renderer: function(value) {
				if (Ext.getStore('EconomyDocStore').getById(value) != undefined) {
					return Ext.getStore('EconomyDocStore').getById(value).get('year');
				} else {
					return value;
				}
        	},
		},
		{ text: 'Kurs', dataIndex: 'courseInstanceId', editable: false, filter: 'string', align: 'left', flex: 2,
         	renderer: function(value) {
				if (Ext.getStore('CourseInstanceStore').getById(value) != undefined) {
					return Ext.getStore('CourseInstanceStore').getById(value).get('courseDesignation');
				} else {
					return value;
				}
        	},
		},
		{ text: 'Student', dataIndex: 'studentName', /* editor: 'textfield' ,*/ editable: false, filter: 'string', align: 'left', flex: 2},
		{ text: 'Reg inst.', dataIndex: 'ibgReg', filter: 'boolean', align: 'left', width:170,
         	renderer: function(value) {
				if (value) {
					return 'IBG';
				} else {
					return 'Annan institution';
				}
			}
		},
		{ text: 'Koord. inst.', dataIndex: 'department', /* editor: 'textfield',  */editable: false, filter: 'list', align: 'left', flex: 1},
		{ text: 'Anteckningar', dataIndex: 'note', editor: 'textfield', filter: 'string', align: 'left', flex: 3 }

	],


	config : {
	}


});

