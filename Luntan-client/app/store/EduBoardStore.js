Ext.define('Luntan.store.EduBoardStore', {
    extend: 'Ext.data.Store',

    alias: 'store.eduboards',

	idProperty: 'id',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'displayname', type: 'string'}
    ],
	
	proxy: {
         type: 'rest',
         url: Luntan.data.Constants.BASE_URL.concat('rest/eduboards'),
         reader: {
             type: 'json'
         }
     }, 
    autoLoad: false
});

