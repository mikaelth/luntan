Ext.define('Luntan.view.main.CourseTaskList', {
    extend: 'Luntan.view.main.BasicYearListGrid',
    requires: [
    ],
    xtype: 'citasklist',
	reference: 'ciTaskList',

    title: 'Kurstillfällen, uppdragsfördelning',

	controller: 'courseinstancetasklist',
	viewModel: {type:'coursemodel'},


	bind: {store: '{citaskstore}'},
	
	features: [{ ftype: 'grouping',startCollapsed: false }],

    columns: [
		{ text: 'Kursgrupp', dataIndex: 'courseGroup', filter: 'list', align: 'left', width: 150 },
		{ text: 'Kurs', dataIndex: 'courseDesignation', filter: 'string', align: 'left', filter: 'string', flex: 1},
		{ text: 'Extra', dataIndex: 'extraDesignation', filter: 'list', align: 'left', width: 100, 
         	renderer: function(value) {
				if (Ext.getStore('CIDesignationStore').getById(value) != undefined) {
					return Ext.getStore('CIDesignationStore').getById(value).get('displayname');
				} else {
					return value;
				}
        	}
		},
		{ text: 'Studentantal', dataIndex: 'modelStudentNumber', filter: 'number', align: 'left', width: 100 },
		{ text: 'Källa', dataIndex: 'modelCase', filter: 'list', align: 'left', width: 90 },
//		{ text: 'Regs', dataIndex: 'onRegs', xtype: 'checkcolumn', filter: 'boolean', align: 'left', width: 30 },
		{ text: 'IBG', dataIndex: 'IBG', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'number', align: 'left', width: 80},
		{ text: 'ICM', dataIndex: 'ICM', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'number', align: 'left', width: 80 },
		{ text: 'IEG', dataIndex: 'IEG', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'number', align: 'left', width: 80 },
		{ text: 'IOB', dataIndex: 'IOB', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'number', align: 'left', width: 80},
		{ text: 'Kursledare', dataIndex: 'courseLeader', align: 'left', flex: 1,
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
				bind: {store: '{seniorStaff}'},
				queryMode: 'local',
				lastQuery: '',
				displayField: 'name',
			    valueField: 'employeeNumber'
			}
		},
		{ text: 'Kommentarer', dataIndex: 'note', editor: 'textfield', filter: 'string', align: 'left', flex: 2 }

	],

	listeners: {
	},
	config : {
	}


});

