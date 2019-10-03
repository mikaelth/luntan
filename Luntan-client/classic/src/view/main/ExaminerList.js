Ext.define('Luntan.view.main.ExaminerList', {
    extend: 'Luntan.view.main.BasicListGrid',
    requires: [
    ],
    xtype: 'examinerlist',
	reference: 'examinerList',

    title: '<b>Examinatorer</b>',

	controller: 'courseinstancelist',
	viewModel: 'coursemodel',

	bind: {
		store: '{examiners}',
		title: '<b>Examinatorer {current.edoc.year}</b>'
	},
		

    columns: [
		{ text: 'Id', dataIndex: 'employeeNumber', editor: 'textfield', filter: 'string', align: 'left', flex: 1},
		{ text: 'Namn', dataIndex: 'name', editor: 'textfield', filter: 'string', align: 'left', width: 100},
		{ text: 'Email', dataIndex: 'email', editor: 'textfield', filter: 'string', align: 'left', flex: 2 }

	],

	config : {
	}


});

