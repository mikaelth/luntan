Ext.define('Luntan.store.EDGStore', {
    extend: 'Ext.data.Store',
    requires: ['Ext.data.proxy.Rest', 'Ext.data.reader.Json'],
    alias: 'store.edgs',
	model: 'Luntan.model.EconomyDocGrant',
	sorters: {property:'code', direction: 'ASC'},
//    autoLoad: true
    autoLoad: false
});

//Ext.create('PorTableClient.store.UserStore').load();
