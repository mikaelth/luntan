Ext.define('Luntan.store.CourseGroupStore', {
    extend: 'Ext.data.Store',

    alias: 'store.coursegroups',

    fields: [
        {name: 'label', type: 'string'}
    ],
	
	proxy: {
         type: 'rest',
         url: Luntan.data.Constants.BASE_URL.concat('rest/coursegroups'),
         reader: {
             type: 'json'
         }
     }, 
    autoLoad: false
});

