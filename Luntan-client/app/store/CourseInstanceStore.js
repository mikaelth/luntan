Ext.define('Luntan.store.CourseInstanceStore', {
    extend: 'Ext.data.Store',
    requires: ['Ext.data.proxy.Rest', 'Ext.data.reader.Json'],
    alias: 'store.courseinstances',
	model: 'Luntan.model.CourseInstance', 
    autoLoad: false
});
