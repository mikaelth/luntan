Ext.define('Luntan.store.ProgrammeStore', {
    extend: 'Ext.data.Store',
    requires: ['Ext.data.proxy.Rest', 'Ext.data.reader.Json'],
    alias: 'store.programmes',
	model: 'Luntan.model.Programme',
	sorters: {property:'code', direction: 'ASC'},
//	groupField: 'courseGroup',
//    autoLoad: true
    autoLoad: false
});

//Ext.create('PorTableClient.store.UserStore').load();
