Ext.define('Luntan.store.CourseRegStore', {
    extend: 'Ext.data.Store',
    requires: ['Ext.data.proxy.Rest', 'Ext.data.reader.Json'],
    alias: 'store.courseregs',
	model: 'Luntan.model.IndCourseReg',
	sorters: {property:'code', direction: 'ASC'},
//	groupField: 'courseGroup',
//    autoLoad: true
    autoLoad: false
});

