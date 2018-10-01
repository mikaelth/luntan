Ext.define('Luntan.store.CourseStore', {
    extend: 'Ext.data.Store',
    requires: ['Ext.data.proxy.Rest', 'Ext.data.reader.Json'],
    alias: 'store.courses',
	model: 'Luntan.model.Course',
	sorters: {property:'code', direction: 'ASC'},
	groupField: 'courseGroup',
//    autoLoad: true
    autoLoad: false
});

//Ext.create('PorTableClient.store.UserStore').load();
