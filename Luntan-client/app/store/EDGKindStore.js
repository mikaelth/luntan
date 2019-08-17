Ext.define('Luntan.store.EDGKindStore', {
    extend: 'Ext.data.Store',

    alias: 'store.grantkindstore',

	idProperty: 'id',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'displayname', type: 'string'}
    ],
	
	proxy: {
         type: 'rest',
         url: Luntan.data.Constants.BASE_URL.concat('rest/grantkinds'),
         reader: {
             type: 'json'
         }
     }, 
    autoLoad: false
});

