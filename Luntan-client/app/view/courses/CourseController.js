Ext.define('Luntan.view.courses.CourseController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.courselist',

    
    onCreate: function()
    {
        var grid = this.getView();
         grid.plugins[0].cancelEdit();

        // Create a model instance
        var r = Ext.create('Luntan.model.Course');
		var rec = grid.getStore().insert(0, r);
        grid.plugins[0].startEdit(rec[0]);
        
    },

   	onBeforeRender: function (grid) {
   	},

   	onUpdateInactive: function () {
   	},

   	init: function (view) {

						
/* 
		var tb = view.down('toolbar');
		
		tb.insert(1,
			{
				xtype: 'button',
				showText: true,
				tooltip:'Kontrollerar om det i kursplanen anges att Kursen är avvecklad',
				text: 'Uppdatera inaktiva',
				reference: 'btnInactiveUpdate',
				disabled: false,
				listeners: {
				  click: 'onUpdateInactive'
				}
			});			
        
 */
	}

});
