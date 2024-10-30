Ext.define('Luntan.store.IndCourseTeacherStore', {
    extend: 'Ext.data.Store',
    requires: ['Ext.data.proxy.Rest', 'Ext.data.reader.Json'],
    alias: 'store.indcourseteacher',
	model: 'Luntan.model.IndCourseTeacher',
	sorters: {property:'rank', direction: 'ASC'},
    autoLoad: false
});

