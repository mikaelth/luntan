Ext.define('Luntan.view.main.FMList', {
    extend: 'Luntan.view.main.BasicListGrid',
    requires: [
    ],
    xtype: 'fmlist',
	reference: 'fmList',

    title: 'Betalningsmodeller',

	controller: 'fundingmodel',
	viewModel: 'fundingmodel',


	bind: {store: '{fundingmodels}'},
	
	features: [{ ftype: 'grouping',startCollapsed: true }],

    columns: [
		{ text: 'Antal kurstillfällen', dataIndex: 'numCourseInstances', filter: 'number', align: 'left', width: 200},
		{ text: 'Benämning', dataIndex: 'designation', editor: 'textfield', filter: 'string', align: 'left', width: 100, flex: 1},
		{ text: 'Uttryck', dataIndex: 'expression', editor: 'textfield', filter: 'string', align: 'left', flex: 1 },
		{ text: 'Anteckningar', dataIndex: 'note', editor: 'textfield', filter: 'string', align: 'left', flex: 2 }

	],

	config : {
	}


});

