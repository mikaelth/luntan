Ext.define('Luntan.store.ICTKindStore', {
    extend: 'Ext.data.Store',

    alias: 'store.ictkinds',

	idProperty: 'id',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'displayname', type: 'string'}
    ],
	
	proxy: {
         type: 'rest',
         url: Luntan.data.Constants.BASE_URL.concat('rest/ictkinds'),
         reader: {
             type: 'json'
         }
     }, 
    autoLoad: false
});

