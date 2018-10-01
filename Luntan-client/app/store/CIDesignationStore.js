Ext.define('Luntan.store.CIDesignationStore', {
    extend: 'Ext.data.Store',

    alias: 'store.cidesignations',

    fields: [
        {name: 'label', type: 'string'}
    ],
	
	proxy: {
         type: 'rest',
         url: Luntan.data.Constants.BASE_URL.concat('rest/cidesignations'),
         reader: {
             type: 'json'
         }
     }, 
    autoLoad: false
});

