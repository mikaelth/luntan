Ext.define('Luntan.view.main.FMTabledList', {
    extend: 'Luntan.view.main.BasicListGrid',
    requires: [
    ],
    xtype: 'fmtabledlist',
	reference: 'fmTabledList',

    title: 'Tabellerade värden per student och ECTS',

	controller: 'fundingtable',
	viewModel: 'fundingmodel',


	bind: {store: '{tabled}'},
	
//	features: [{ ftype: 'grouping',startCollapsed: true }],

    columns: [
		{ text: 'Antal studenter', dataIndex: 'number', editor: 'textfield', filter: 'string', align: 'left', width: 100, flex: 1},
		{ text: 'Betalning', dataIndex: 'value', editor: 'textfield', filter: 'string', align: 'left', flex: 1 }

	],

	config : {
	}


});

