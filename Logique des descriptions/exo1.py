#%%
from owlready2 import *

onto = get_ontology("http://testxyz.org/onto.owl") #create ontology using iri

with onto: #defining our ontology

    ##Defining concepts##
    class Personne(Thing):
        pass

    class Femelle(Thing):
        pass

    class Male(Thing):
        pass

    class Entreprise(Thing):
        pass

    AllDisjoint([Male, Femelle]) 


    ##Defining roles##
    class travaille(Personne >> Entreprise): 
       pass

    class derige(Personne >> Entreprise): 
       pass

    class derige_par(ObjectProperty):
        inverse_property = derige


    
        ##Defining composed entities##
    class Femme(Thing):
        equivalent_to = [Personne & Femelle] 

    class Homme(Thing):
        equivalent_to = [Personne & Male]

    class Employee(Thing):
        equivalent_to = [Femme & travaille.some(Entreprise)]

    class Employe(Thing):
        equivalent_to = [Homme & travaille.some(Entreprise)]

    class Directeur(Thing):
        equivalent_to = [Personne & derige.some(Entreprise)]

    ##defining instances ABOX##
        
    Rahil = Employee("Rahil")
    Lydia = Employee("Lydia")
    Mohamed = Directeur("Mohamed")
    SONATRACH = Entreprise("SONATRACH")

    AllDisjoint([Employee, Employe])
    AllDisjoint([Rahil, Lydia])

    
    sync_reasoner_pellet(infer_property_values=True)
    onto.save(file = "tp_rc1.owl", format = "rdfxml")
    
     # Vérifier les assertions
    print("Rahil est une employee:", Rahil in Employee.instances())
    print("Lydia une employee:", Lydia in Employee.instances())

#%%
with onto:

    SONATRACH = onto.Entreprise()

    Rahil = onto.Employee()

    Lydia = onto.Employee()

    Mohamed = onto.Directeur()




    Rahil.travaille = [SONATRACH]
    Lydia.travaille = [SONATRACH]

    sync_reasoner_pellet(infer_property_values=True)
    onto.save(file = "tp_rc2.owl", format = "rdfxml")

# %%