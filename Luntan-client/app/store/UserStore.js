Ext.define('Luntan.store.UserStore', {
    extend: 'Ext.data.Store',
    requires: ['Ext.data.proxy.Rest', 'Ext.data.reader.Json'],
    alias: 'store.users',
	model: 'Luntan.model.User',
	sorters: {property:'familyName', direction: 'ASC'},
//    autoLoad: true
    autoLoad: false
});

//Ext.create('PorTableClient.store.UserStore').load();
