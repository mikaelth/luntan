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
		{ text: 'Kursgrupp', dataIndex: 'courseGroup', align: 'left', filter: 'string', width: 150 },
		{ text: 'Kurs', dataIndex: 'courseDesignation', align: 'left', filter: 'string', flex: 1,         	
		},
		{ text: 'Skattat studentantal', dataIndex: 'modelStudentNumber', filter: 'string', align: 'left', width: 150 },
		{ text: 'IBG', dataIndex: 'IBG', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'number', align: 'left', width: 80},
		{ text: 'ICM', dataIndex: 'ICM', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'number', align: 'left', width: 80 },
		{ text: 'IEG', dataIndex: 'IEG', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'number', align: 'left', width: 80 },
		{ text: 'IOB', dataIndex: 'IOB', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'number', align: 'left', width: 80},
		{ text: 'Kommentarer', dataIndex: 'note', editor: 'textfield', filter: 'string', align: 'left', flex: 2 }

	],

	listeners: {
	},
	config : {
	}


});

