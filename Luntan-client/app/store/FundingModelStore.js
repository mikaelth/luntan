Ext.define('Luntan.store.FundingModelStore', {
    extend: 'Ext.data.Store',
    requires: ['Ext.data.proxy.Rest', 'Ext.data.reader.Json'],
    alias: 'store.fundingmodels',
	model: 'Luntan.model.FundingModel',
    autoLoad: false
});
