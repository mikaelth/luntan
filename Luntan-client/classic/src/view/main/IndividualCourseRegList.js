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

    columns:
    [

		{ xtype: 'checkcolumn', text: 'Klar', dataIndex: 'studentDone', editor: 'checkboxfield', editable: true, filter: 'boolean', align: 'center', width: 80, filter: 'boolean'},
		{ xtype: 'checkcolumn', text: 'KURT', dataIndex: 'courseEvalSetUp', editor: 'checkboxfield', editable: true, filter: 'boolean', align: 'center', width: 80, filter: 'boolean'},
		{ xtype: 'datecolumn',text: 'Registreriingsdatum', dataIndex: 'registrationDate', editable: false, format:'Y-m-d', filter: 'date', align: 'left', width: 150},
		{ xtype: 'datecolumn',text: 'Startdatum', dataIndex: 'startDate', format:'Y-m-d', editor: 'datefield', filter: 'date', align: 'left', width: 150},
		{ text: 'Kurs', dataIndex: 'courseInstanceId', filter: 'string', align: 'left', flex: 2,
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
		{ text: 'Reg inst.', dataIndex: 'ibgReg', filter: 'list', align: 'left', width:170,
         	renderer: function(value) {
				if (value) {
					return 'IBG'
				} else {
					return 'Annan institution'
				}
        	},
			editor: {
				xtype: 'combobox',
				typeAhead: true,
				triggerAction: 'all',
				store: new Ext.data.ArrayStore({
					fields: ['id', 'value'],
					data: [['IBG', true],['Annan institution', false]]
				}),
				queryMode: 'local',
				lastQuery: '',
				displayField: 'id',
			    valueField: 'value',
 			}
		},
		{ text: 'Koordinerande', dataIndex: 'department', filter: 'list', align: 'left', width: 150},
		{ text: 'Anteckningar', dataIndex: 'note', editor: 'textfield', filter: 'string', align: 'left', flex: 3 }

	],

	config : {
	}


});

