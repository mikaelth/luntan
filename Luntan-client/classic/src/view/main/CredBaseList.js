Ext.define('Luntan.view.main.CredBaseList', {
    extend: 'Luntan.view.main.BasicListGrid',
    requires: [
    ],
    xtype: 'credbaselist',
	reference: 'credbaselist',

    title: 'Betalningsunderlag',

	controller: 'credbaselist',


	listeners: {
		storechange: function (view) {
			console.log("storechange", view);
			var store = view.getStore().hasOwnProperty('source') ? view.getStore().getSource() : view.getStore();
			store.on({'update': this.controller.onStoreContentUpdated, scope: this});
			store.on({'remove': this.controller.onStoreContentRemoved, scope: this});

		}
	},
	bind: {
		store: '{credbasis}',
		title: '<b>Betalningsunderlag</b>'
	},
	
	features: [{ ftype: 'grouping',startCollapsed: false }],

//    columns:  [{ xtype: 'IndividualCourseRegColumns'}],
 
	columns: 
    [

		{ text: 'Antal registreringar', dataIndex: 'numberOfRegs', filter: 'number', align: 'left', width: 150 },
		{ xtype: 'datecolumn',text: 'Skapad', dataIndex: 'createdDate', format:'Y-m-d', filter: 'date', align: 'left', width: 150},
		{ xtype: 'datecolumn',text: 'Skickad', dataIndex: 'sent', format:'Y-m-d', editor: 'datefield', filter: 'date', align: 'left', width: 150},
		{ text: 'Anteckningar', dataIndex: 'note', editor: 'textfield', filter: 'string', align: 'left', flex: 1 }

	],
 

	config : {
	}


});

