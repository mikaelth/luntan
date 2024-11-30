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
        r.set('registrationDate',new Date());
		r.set('ibgReg',true);
		
		var rec = grid.getStore().insert(0, r);
        grid.plugins[0].startEdit(rec[0]);

    },

   	onBeforeRender: function (grid) {
   	},

   	onUpdateInactive: function () {
   	},

   	onIndCourseTeachers: function () {
		console.log("onIndCourseTeachers");
   		var year = this.getViewModel().get('current.edoc.year');
   		window.open(Luntan.data.Constants.BASE_URL.concat('view/ictds?year=').concat(year));
   	},

   	init: function (view) {

		var tb = view.down('toolbar');

		tb.insert(2,
			{
				xtype: 'button',
				showText: true,
				tooltip:'Visa lärare inblandade i individuella kurser för aktuellt år',
				text: 'Sammanställning lärare',
				reference: 'btnIndCourseTeachers',
				disabled: true,
				listeners: {
				  click: 'onIndCourseTeachers'
				}
			});

		view.lookupReference('comboCurrentYear').addListener('select', function(combo,record) {
			this.lookupReferenceHolder().lookupReference('btnIndCourseTeachers').enable();
			return true;
        });

/*

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
