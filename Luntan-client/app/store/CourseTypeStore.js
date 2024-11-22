Ext.define('Luntan.store.CourseTypeStore', {
    extend: 'Ext.data.Store',

    alias: 'store.coursetypes',

	idProperty: 'id',
/* 
    fields: [
        {name: 'id', type: 'string'},
        {name: 'displayname', type: 'string'}
    ],
 */
    fields: [
        {name: 'label', type: 'string'}
    ],
	
	proxy: {
         type: 'rest',
         url: Luntan.data.Constants.BASE_URL.concat('rest/coursetypes'),
         reader: {
             type: 'json'
         }
     }, 
    autoLoad: false
});

