Ext.define('Luntan.view.courses.RegistrationModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.registrationmodel',

    data: {
		current : {
//			edocId : '',
			edoc: null,
			reg : null
		}
    },
    
    stores: {
		depts: 'DepartmentStore',
		
		ictKinds: 'ICTKindStore',
		
 		usedYears: {
 			type:'chained',
 			source:'EconomyDocStore',
 			sorters: [{property:'year', direction: 'DESC'}]
 		},

		registrations : {
			type: 'chained',
			source: 'IndCourseRegStore',
			filters: [
				{property: 'economyDocId', value: '{current.edoc.id}', exactMatch: true}
			],
			sorters: [{property:'courseDesignation', direction: 'ASC'}]
		},

		teachers : {
			type: 'chained',
			source: 'TeacherStore',
			sorters: [{property:'name', direction: 'ASC'}]
		},

		icis : {
			type: 'chained',
			source: 'CourseInstanceStore',
			filters: [
 				{property: 'individualYearlyCourse', value: true, exactMatch: true},
				{property: 'economyDocId', value: '{current.edoc.id}', exactMatch: true}
			],
			sorters: [{property:'courseDesignation', direction: 'ASC'}]
		},

		icts : {
			type: 'chained',
			source: 'IndCourseTeacherStore',
			filters: [
 				{property: 'assignmentId', value: '{current.reg.id}', exactMatch: true}, 
			],			
		},
 
	}, 
	
	formulas: {

        workingEDoc: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{comboCurrentYear.selection}', //--> reference configurated on the grid view (reference: comboCurrentYear)
                deep: true
            },
            get: function(edoc) {
            	this.set('current.edoc', edoc);
                return edoc;
            }
        },
        currentReg: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{coursereglist.selection}', //--> reference configurated on the grid view (reference: ciList)
                deep: true
            },
            get: function(reg) {
            	this.set('current.reg', reg);
                return reg;
            }
        },

 		disableEditBtns :  function (get) {
 			return get('current.edoc') === null;
 		},


 	}

});
