Ext.define('Luntan.store.TeacherStore', {
    extend: 'Ext.data.Store',
    requires: ['Ext.data.proxy.Rest', 'Ext.data.reader.Json'],
    alias: 'store.teacher',
	model: 'Luntan.model.Teacher',
//	sorters: [{property:'familyName', direction: 'ASC'},{property:'givenName', direction: 'ASC'}],
    autoLoad: false
});

