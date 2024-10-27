Ext.define('Luntan.view.courses.RegistrationModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.registrationmodel',

    data: {
		current : {
			edocId : '',
			edoc: null,
			reg : null
		}
    },
    
    stores: {
		depts: 'DepartmentStore',

 		usedYears: {
 			type:'chained',
 			source:'EconomyDocStore',
 			sorters: [{property:'year', direction: 'DESC'}]
 		},

		registrations : {
			type: 'chained',
			source: 'CourseRegStore',
			filters: [
				{property: 'economyDocId', value: '{current.edoc.id}', exactMatch: true}
			],
			sorters: [{property:'courseDesignation', direction: 'ASC'}]
		},
/*  
  		examinerslistings: {
 			type:'chained',
 			source:'ExaminersListStore',
 			sorters: [
 				{property:'decided', direction: 'ASC'},
 				{property:'decisionDate', direction: 'DESC'}
 			],
			listeners: {
				update: function ( store, record, operation, modifiedFieldNames, details, eOpts) {
					if (operation == 'commit' && modifiedFieldNames.includes('id') ) {
						// This means a new record is created at the server
						Ext.getStore('ExaminerStore').reload();
					}
				}
			}
 
  		},

 

		listedteachers : {
			type: 'chained',
			source: 'TeacherStore'
		},

		teachers : {
			type: 'chained',
			source: 'TeacherStore',
			filters:[{property:'examinerEligible', value: true, exactMatch: true}],
			sorters: [{property:'name', direction: 'ASC'}]
		},
 */

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

/* 
		examiners : {
			type: 'chained',
			source: 'ExaminerStore',
			filters: [
 				{property: 'courseId', value: '{current.cid}', exactMatch: true}, 
 				{property: 'decided', value: false, exactMatch: true}
			],
//			sorters: {property:'rank', direction: 'ASC'},
			nextRank : function() { 
				var rs = this.data.items, v = 0; 
				for(var i = 0; i < rs.length; i++){ 
					v = Math.max(v, (1 * rs[i].data['rank']) || 0); 
				} 
				return v+1; 
			} 			
			
		},
 */
/*
		listedexaminers : {
			type: 'chained',
			source: 'ExaminerStore',
			filters: [
				{property: 'decisionId', value: '{current.did}', exactMatch: true} 
			],
			sorters: [{property:'courseId', direction: 'ASC'},{property:'rank', direction: 'ASC'}]
 
			nextRank : function() { 
				var rs = this.data.items, v = 0; 
				for(var i = 0; i < rs.length; i++){ 
					v = Math.max(v, (1 * rs[i].data['rank']) || 0); 
				} 
				return v+1; 
			} 			

			
		}
 */		    	
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

/*
        currentCourse: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{courselist.selection.id}', //--> reference configurated on the grid view (reference: courselist)
                deep: true
            },
            get: function(cid) {
            	this.set('current.cid', cid);
                return cid;
            }
 
		},
        currentFormalCourse: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{courselist.selection}', //--> reference configurated on the grid view (reference: courselist)
                deep: true
            },
            get: function(cCourse) {
            	this.set('current.cCourse', cCourse);
                return cCourse;
            }
 
		},
        currentDecision: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{examinerslisting.selection.id}', //--> reference configurated on the grid view (reference: examinerslisting)
                deep: true
            },
            get: function(did) {
            	this.set('current.did', did);
                return did;
            }
 
		}

 */ 	
 	}

});
