Ext.define('Luntan.view.main.CourseTaskList', {
    extend: 'Luntan.view.main.BasicYearListGrid',
    requires: [
    ],
    xtype: 'citasklist',
	reference: 'ciTaskList',

    title: 'Kurstillfällen, uppdragsfördelning',

	controller: 'courseinstancelist',
	viewModel: {type:'coursemodel'},


	bind: {store: '{citaskstore}'},
//	store: 'CIStore',
	
	features: [{ ftype: 'grouping',startCollapsed: false }],

    columns: [
		{ text: 'Kursgrupp', dataIndex: 'courseGroup', align: 'left', width: 150 },
		{ text: 'Kurs', dataIndex: 'courseDesignation', align: 'left', flex: 2},
		{ text: 'Extra benämning', dataIndex: 'extraDesignation', filter: 'string', align: 'left', flex: 1 },
		{ text: 'Skattat studentantal', dataIndex: 'modelStudentNumber', filter: 'string', align: 'left', flex: 1 },
		{ text: 'IBG', dataIndex: 'IBG', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'string', align: 'left', flex: 1 },
		{ text: 'ICM', dataIndex: 'ICM', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'string', align: 'left', flex: 1 },
		{ text: 'IEG', dataIndex: 'IEG', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'string', align: 'left', flex: 1 },
		{ text: 'IOB', dataIndex: 'IOB', xtype: 'numbercolumn', format: '0.00', editor: 'textfield', filter: 'string', align: 'left', flex: 1 },
		{ text: 'Kommentarer', dataIndex: 'note', editor: 'textfield', filter: 'string', align: 'left', flex: 3 }

	],

	config : {
	}


});

