Ext.define('Luntan.store.UserRoleTypeStore', {
    extend: 'Ext.data.Store',

    alias: 'store.userroletypes',

    fields: [
        {name: 'label', type: 'string'}
    ],
	
	proxy: {
         type: 'rest',
         url: Luntan.data.Constants.BASE_URL.concat('rest/userroletypes'),
         reader: {
             type: 'json'
         }
     }, 
    autoLoad: false
});

