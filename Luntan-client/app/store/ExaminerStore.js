Ext.define('Luntan.store.ExaminerStore', {
    extend: 'Ext.data.Store',
    requires: ['Ext.data.proxy.Rest', 'Ext.data.reader.Json'],
    alias: 'store.examiner',
	model: 'Luntan.model.Examiner',
	sorters: {property:'rank', direction: 'ASC'},
    autoLoad: false
});

