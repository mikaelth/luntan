Ext.define('Luntan.store.ExaminersListStore', {
    extend: 'Ext.data.Store',
    requires: ['Ext.data.proxy.Rest', 'Ext.data.reader.Json'],
    alias: 'store.examinerlist',
	model: 'Luntan.model.ExaminersList',
	sorters: {property:'decisionDate', direction: 'DESC'},
    autoLoad: false
});

