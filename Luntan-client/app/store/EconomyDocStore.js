Ext.define('Luntan.store.EconomyDocStore', {
    extend: 'Ext.data.Store',
    requires: ['Ext.data.proxy.Rest', 'Ext.data.reader.Json'],
    alias: 'store.economydocs',
	model: 'Luntan.model.EconomyDoc',
	sorters: {property:'year', direction: 'DESC'},
    autoLoad: false
});

