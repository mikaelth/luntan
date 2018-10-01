Ext.define('Luntan.store.DepartmentStore', {
    extend: 'Ext.data.Store',

    alias: 'store.departments',

    fields: [
        {name: 'label', type: 'string'}
    ],
	
	proxy: {
         type: 'rest',
         url: Luntan.data.Constants.BASE_URL.concat('rest/departments'),
         reader: {
             type: 'json'
         }
     }, 
    autoLoad: false
});

