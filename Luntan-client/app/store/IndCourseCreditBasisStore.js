Ext.define('Luntan.store.IndCourseCreditBasisStore', {
    extend: 'Ext.data.Store',
    requires: ['Ext.data.proxy.Rest', 'Ext.data.reader.Json'],
    alias: 'store.coursecredbasis',
	model: 'Luntan.model.IndCourseCreditBasis',
//	sorters: {property:'code', direction: 'ASC'},
//    autoLoad: true
    autoLoad: false
});

