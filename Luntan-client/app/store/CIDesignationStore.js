Ext.define('Luntan.store.CIDesignationStore', {
    extend: 'Ext.data.Store',

    alias: 'store.cidesignations',

	idProperty: 'id',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'displayname', type: 'string'}
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

