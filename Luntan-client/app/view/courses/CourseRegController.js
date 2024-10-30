Ext.define('Luntan.view.courses.CourseRegController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.coursereglist',

    
    onCreate: function()
    {
        var grid = this.getView(),
        	thisEDoc = this.getViewModel().get('current.edoc.id');
         grid.plugins[0].cancelEdit();
//this.view.up().up().lookupReference('ictList').controller.onCreate();
        // Create a model instance
        var r = Ext.create('Luntan.model.IndCourseReg');
        r.set('economyDocId',thisEDoc);

		var rec = grid.getStore().insert(0, r);
        grid.plugins[0].startEdit(rec[0]);
        
    },

   	onBeforeRender: function (grid) {
   	},

   	onUpdateInactive: function () {
   	},

   	init: function (view) {

		var tb = view.down('toolbar');
/* 						
		tb.insert(2,
			{
				xtype: 'button',
				showText: true,
				tooltip:'Slå upp kurser i studieplaner',
				text: 'Kurser per program',
				reference: 'btnProgramCourses',
				disabled: false,
				listeners: {
				  click: 'onProgramCourses'
				}
			});			


		
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
