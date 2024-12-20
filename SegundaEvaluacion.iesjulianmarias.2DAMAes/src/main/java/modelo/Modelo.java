package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import clasesHibernate.Departamentos;
import clasesHibernate.Empleados;
import jakarta.persistence.TypedQuery;


public class Modelo {


	private static final Configuration cfg = new Configuration().configure();
	private static final SessionFactory sf = cfg.buildSessionFactory();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//anadirDpto("Personal","Zamora");
		/*System.out.println("Mostrar todos");
		ArrayList<Departamentos> dptos = listarDptos();
		for(Departamentos dpto: dptos) {
			System.out.println(dpto);
		}*/
		/*System.out.println("Mostrar departamentos en pamplona");
		ArrayList<Departamentos> dptos2 = listarDptos("Pamplona");
		for(Departamentos dpto: dptos2) {
			System.out.println(dpto);
		}*/
		/*System.out.println("Mostrar departamentos personal");
		 dptos = listarDptosNombre("Personal");
		for(Departamentos dpto: dptos) {
			System.out.println(dpto);
		}*/
		//System.out.println("Mostrar 41");

		//System.out.println(listarDptos(41));
		 
		//Boolean hecho = modifcarDpto(44,"nuevoNombre","nuevaLocalidad");
		//System.out.println("Modificar Dpto");
		//System.out.println(modificarDpto(44,null,"Pamplona")?"Dpto modificado con éxito":"Dpto no modificado");
		//Borrar Departamento por id
		//borrarDpto(50);
		//borrarDpto por nombre
		//borrarDpto("Personal");
		
		/* boolean anadirEmpleado = anadirEmpleado("Clauda", "Garcia", "Gómez", "Propaganda");
		    if (anadirEmpleado) {
		        System.out.println("Empleado añadido con éxito.");
		    } else {
		        System.out.println("No se pudo añadir el empleado.");
		    }*/
		
		    System.out.println("Lista de empleados:");
		    HashSet<Empleados> empleados = listarEmpleados();
			for(Empleados empleado: empleados) {
				System.out.println(empleado);
			}
   
		    System.out.println("Lista de empleados Ventas:");
		    HashSet<Empleados> empleados2 = listarEmpleados("Ventas");
			for(Empleados empleado: empleados2) {
				System.out.println(empleado);
			}
   
   
			eliminarEmpleado("Julia","Cantara","Perez");
			cambiarEmpleadoDpto(18,"Personal");
		    
	}

	/*
	 * @param id
	 * @param nombre
	 * @param localidad
	 * @return true si se ha modificado el departamento, false en caso contrario
	 */
	private static Boolean modificarDpto(int id, String nombre, String localidad) {
		// TODO Auto-generated method stub
		Boolean flag = false;
		Session sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		
		Departamentos dpto = sesion.get(Departamentos.class,id);
		if(nombre!=null) {
			dpto.setDnombre(nombre);
			flag= true;
		}
		if(localidad!=null) {
			dpto.setLoc(localidad);
			flag = true;
		}
		sesion.merge(dpto);
		t.commit();
		sesion.close();
		return flag;
	}

	private static ArrayList<Departamentos> listarDptos() {
		// TODO Auto-generated method stub
		Session sesion = sf.openSession();
		String hql = "from Departamentos";
		TypedQuery<Departamentos> consulta = sesion.createQuery(hql,Departamentos.class);
		ArrayList<Departamentos> dptos = (ArrayList<Departamentos>) consulta.getResultList();
		sesion.close();
		return dptos;
	}
	private static ArrayList<Departamentos> listarDptos(String localidad) {
		// TODO Auto-generated method stub
		Session sesion = sf.openSession();
		String hql = "from Departamentos where loc ='" + localidad+"'";
		TypedQuery<Departamentos> consulta = sesion.createQuery(hql,Departamentos.class);
		ArrayList<Departamentos> dptos = (ArrayList<Departamentos>) consulta.getResultList();
		sesion.close();

		return dptos;
	}
	private static Departamentos listarDptos(int dptoId) {
		Session sesion = sf.openSession();
		String hql = "from Departamentos where deptNo=" + dptoId ;
		TypedQuery<Departamentos> consulta =  sesion.createQuery(hql, Departamentos.class);
		Departamentos dpto =consulta.getSingleResult();
		sesion.close();
		return dpto;
	}
	private static ArrayList<Departamentos> listarDptosNombre(String nombre) {
		// TODO Auto-generated method stub
		Session sesion = sf.openSession();
		String hql = "from Departamentos where dnombre ='" + nombre+"'";
		TypedQuery<Departamentos> consulta = sesion.createQuery(hql,Departamentos.class);
		ArrayList<Departamentos> dptos = (ArrayList<Departamentos>) consulta.getResultList();
		sesion.close();

		return dptos;
	}
	private static void anadirDpto(String dptoNombre, String dptoLocalidad) {
		// TODO Auto-generated method stub
		Session sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		Departamentos dpto =  new Departamentos(dptoNombre,dptoLocalidad,null);
		sesion.persist(dpto);
		t.commit();
		sesion.close();
	}
	private static void borrarDpto(int id) {
	    Session sesion = sf.openSession();
	    Transaction t = sesion.beginTransaction();

	    try {
	        // Buscar el departamento por su ID
	        Departamentos dpto = sesion.get(Departamentos.class, id);
	        
	        if (dpto != null) {
	        
	            sesion.remove(dpto);
	            System.out.println("Departamento eliminado con éxito: " + dpto);
	        } else {
	            System.out.println("No se encontró un departamento con el ID: " + id);
	        }

	        t.commit();
	    } catch (Exception e) {
	        if (t != null) {
	            t.rollback(); 
	        }
	        System.out.println("Error al intentar eliminar el departamento: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        sesion.close();
	    }
	}

	private static void borrarDpto(String dnombre) {
	    Session sesion = sf.openSession();
	    Transaction t = sesion.beginTransaction();
	    Scanner scanner = new Scanner(System.in);

	    try {
	        // Consulta HQL para recuperar departamentos por nombre
	        String hql = "from Departamentos where dnombre = :nombre";
	        List<Departamentos> dptos = sesion.createQuery(hql, Departamentos.class)
	                                          .setParameter("nombre", dnombre)
	                                          .getResultList();

	        if (dptos.isEmpty()) {
	            System.out.println("No se encontraron departamentos con el nombre " + dnombre + ".");
	        } else {
	            System.out.println("Se encontraron " + dptos.size() + " departamentos con el nombre " + dnombre + ".");
	            System.out.println("¿Qué deseas hacer?");
	            System.out.println("1. Eliminar todos los departamentos");
	            System.out.println("2. Revisar y decidir uno por uno");
	            System.out.print("Selecciona una opción (1 o 2): ");

	            int opcion = scanner.nextInt();
	            scanner.nextLine(); 

	            if (opcion == 1) {
	                // Eliminar todos los departamentos encontrados
	                for (Departamentos dpto : dptos) {
	                    sesion.remove(dpto);
	                }
	                System.out.println("Todos los departamentos con el nombre " + dnombre + " han sido eliminados.");
	            } else if (opcion == 2) {
	                // Revisar uno por uno
	                for (Departamentos dpto : dptos) {
	                    System.out.println("¿Deseas eliminar este departamento? " + dpto);
	                    System.out.print("Escribe 's' para sí o 'n' para no: ");
	                    String decision = scanner.nextLine();

	                    if (decision.equalsIgnoreCase("s")) {
	                        sesion.remove(dpto);
	                        System.out.println("Departamento eliminado.");
	                    } else {
	                        System.out.println("Departamento no eliminado.");
	                    }
	                }
	            } else {
	                System.out.println("Opción no válida.");
	            }
	        }

	       
	        t.commit();
	    } catch (Exception e) {
	        // Deshacer la transacción en caso de error
	        if (t != null) {
	            t.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        sesion.close();
	    }
	}


	/*
	 * dpto puede corresponderse con varios. Preguntar cuál mostrando los dptos con ese nombre
	 * dpto no existe, Confirmar que es correcto, si lo es, lo añadimos preguntando la localidad
	 * 
	 * Añadir el empleado al departamento
	 * 
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param nombre departamento
	 * 
	 * */ 
	private static boolean anadirEmpleado(String nombre, String apellido1, String apellido2, String dptoNombre) {
	    Session sesion = sf.openSession();
	    Transaction t = sesion.beginTransaction();
	    Scanner scanner = new Scanner(System.in);
	    boolean empleadoAñadido = false;

	    try {
	        // Buscar departamentos por nombre
	        String hql = "from Departamentos where dnombre = :nombre";
	        List<Departamentos> dptos = sesion.createQuery(hql, Departamentos.class)
	                                          .setParameter("nombre", dptoNombre)
	                                          .getResultList();

	        if (dptos.isEmpty()) {
	            System.out.println("No se encontró ningún departamento con el nombre " + dptoNombre + ".");
	            System.out.print("¿Deseas crear un nuevo departamento con este nombre? (s/n): ");
	            String decision = scanner.nextLine();

	            if (decision.equalsIgnoreCase("s")) {
	                System.out.print("Introduce la localidad del nuevo departamento: ");
	                String localidad = scanner.nextLine();

	                Departamentos nuevoDpto = new Departamentos(dptoNombre, localidad, null);
	                sesion.persist(nuevoDpto);
	                sesion.flush(); // Asegurar que el nuevo departamento se persista antes de asociarlo

	                // TODO Hacer comprobacion de que el empleado no existe ya en la DB. Posibilidad de ROLLBACK
	                Empleados empleado = new Empleados(nuevoDpto, nombre, apellido1, apellido2);
	                sesion.persist(empleado);
	                empleadoAñadido = true;
	                System.out.println("Empleado añadido al nuevo departamento.");
	            }
	        } else if (dptos.size() == 1) {
	            // Solo un departamento encontrado, añadir directamente
	            Departamentos dpto = dptos.get(0);
	            Empleados empleado = new Empleados(dpto, nombre, apellido1, apellido2);
	            sesion.persist(empleado);
	            empleadoAñadido = true;
	            System.out.println("Empleado añadido al departamento: " + dpto);
	        } else {
	            // Varios departamentos encontrados, preguntar al usuario
	            System.out.println("Se encontraron múltiples departamentos con el nombre " + dptoNombre + ":");
	            for (int i = 0; i < dptos.size(); i++) {
	                System.out.println((i + 1) + ". " + dptos.get(i));
	            }
	            System.out.print("Selecciona el número del departamento al que deseas añadir el empleado: ");
	            int seleccion = scanner.nextInt();
	            scanner.nextLine(); // Limpiar el buffer

	            if (seleccion > 0 && seleccion <= dptos.size()) {
	                Departamentos dptoSeleccionado = dptos.get(seleccion - 1);
	                Empleados empleado = new Empleados(dptoSeleccionado, nombre, apellido1, apellido2);
	                sesion.persist(empleado);
	                empleadoAñadido = true;
	                System.out.println("Empleado añadido al departamento: " + dptoSeleccionado);
	            } else {
	                System.out.println("Selección no válida. Operación cancelada.");
	            }
	        }

	        if (empleadoAñadido) {
	            t.commit();
	        } else {
	            t.rollback();
	        }
	    } catch (Exception e) {
	        if (t != null) {
	            t.rollback();
	        }
	        System.out.println("Error al añadir el empleado: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        sesion.close();
	    }

	    return empleadoAñadido;
	}
	
	// TODO Cambiar empleado de dpto
	private static boolean cambiarEmpleadoDpto(int empleadoId, String nuevoDptoNombre) {
	    Session sesion = sf.openSession();
	    Transaction t = sesion.beginTransaction();
	    Scanner scanner = new Scanner(System.in);
	    boolean cambiado = false;

	    try {
	        // Buscar empleado por ID
	        Empleados empleado = sesion.get(Empleados.class, empleadoId);
	        if (empleado == null) {
	            System.out.println("No se encontró un empleado con el ID: " + empleadoId);
	            return false;
	        }

	        // Buscar departamentos por nombre
	        String hql = "from Departamentos where dnombre = :nombre";
	        List<Departamentos> dptos = sesion.createQuery(hql, Departamentos.class)
	                                          .setParameter("nombre", nuevoDptoNombre)
	                                          .getResultList();

	        Departamentos nuevoDpto = null;

	        if (dptos.isEmpty()) {
	            System.out.println("No se encontró un departamento con el nombre " + nuevoDptoNombre + ".");
	            System.out.print("¿Deseas crear un nuevo departamento con este nombre? (s/n): ");
	            String decision = scanner.nextLine();

	            if (decision.equalsIgnoreCase("s")) {
	                System.out.print("Introduce la localidad del nuevo departamento: ");
	                String localidad = scanner.nextLine();
	                nuevoDpto = new Departamentos(nuevoDptoNombre, localidad, null);
	                sesion.persist(nuevoDpto);
	                sesion.flush(); // Asegura que el departamento está disponible en la sesión antes de asociarlo
	                System.out.println("Departamento creado con éxito.");
	            } else {
	                System.out.println("Operación cancelada. No se creó un nuevo departamento.");
	                t.rollback();
	                return false;
	            }
	        } else {
	            // Si hay múltiples departamentos con el mismo nombre
	            if (dptos.size() > 1) {
	                System.out.println("Se encontraron varios departamentos con el nombre " + nuevoDptoNombre + ":");
	                for (int i = 0; i < dptos.size(); i++) {
	                    System.out.println((i + 1) + ". " + dptos.get(i));
	                }

	                System.out.print("Selecciona el número del departamento al que deseas transferir al empleado: ");
	                int seleccion = scanner.nextInt();
	                scanner.nextLine(); // Consumir la nueva línea

	                if (seleccion < 1 || seleccion > dptos.size()) {
	                    System.out.println("Selección inválida. Operación cancelada.");
	                    t.rollback();
	                    return false;
	                }

	                nuevoDpto = dptos.get(seleccion - 1);
	            } else {
	                // Usar el único departamento encontrado
	                nuevoDpto = dptos.get(0);
	            }
	        }

	        // Asignar el nuevo departamento al empleado
	        empleado.setDepartamentos(nuevoDpto);
	        sesion.merge(empleado);
	        cambiado = true;
	        System.out.println("Empleado transferido al departamento: " + nuevoDpto);

	        if (cambiado) {
	            t.commit();
	        } else {
	            t.rollback();
	        }
	    } catch (Exception e) {
	        if (t != null) t.rollback();
	        System.out.println("Error al cambiar el empleado de departamento: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        sesion.close();
	    }
	    return cambiado;
	}


	// TODO Eliminar empleado. Comprobar que el dpto no tiene mas empleados, si es asi se da la opcion de borrar
	private static void eliminarEmpleado(String nombre, String apellido1, String apellido2) {
	    Session sesion = sf.openSession();
	    Transaction t = sesion.beginTransaction();
	    Scanner scanner = new Scanner(System.in);

	    try {
	        // Buscar empleados por nombre, apellido1 y apellido2
	        String hqlEmpleado = "from Empleados where nombre = :nombre and apellido1 = :apellido1 and apellido2 = :apellido2";
	        List<Empleados> empleados = sesion.createQuery(hqlEmpleado, Empleados.class)
	                                          .setParameter("nombre", nombre)
	                                          .setParameter("apellido1", apellido1)
	                                          .setParameter("apellido2", apellido2)
	                                          .getResultList();

	        if (empleados.isEmpty()) {
	            System.out.println("No se encontró un empleado con los datos proporcionados: "
	                + nombre + " " + apellido1 + " " + apellido2);
	            return;
	        }

	        // Manejar múltiples empleados encontrados
	        Empleados empleado = null;
	        if (empleados.size() > 1) {
	            System.out.println("Se encontraron múltiples empleados con los datos proporcionados:");
	            for (int i = 0; i < empleados.size(); i++) {
	                System.out.println((i + 1) + ". " + empleados.get(i));
	            }
	            System.out.print("Selecciona el número del empleado que deseas eliminar: ");
	            int seleccion = scanner.nextInt();
	            scanner.nextLine();

	            if (seleccion < 1 || seleccion > empleados.size()) {
	                System.out.println("Selección inválida. Operación cancelada.");
	                return;
	            }

	            empleado = empleados.get(seleccion - 1);
	        } else {
	            empleado = empleados.get(0); // Único empleado encontrado
	        }

	        // Obtener el departamento del empleado
	        Departamentos dpto = empleado.getDepartamentos();

	        // Eliminar empleado
	        sesion.remove(empleado);
	        System.out.println("Empleado eliminado: " + empleado);

	        // Comprobar si el departamento está vacío
	        String hql = "select count(e) from Empleados e where e.departamentos = :departamento";
	        Long count = sesion.createQuery(hql, Long.class)
	                           .setParameter("departamento", dpto)
	                           .getSingleResult();

	        if (count == 0) {
	            System.out.println("El departamento " + dpto.getDnombre() + " no tiene más empleados.");
	            System.out.print("¿Deseas eliminar este departamento también? (s/n): ");
	            String decision = scanner.nextLine();

	            if (decision.equalsIgnoreCase("s")) {
	                sesion.remove(dpto);
	                System.out.println("Departamento eliminado.");
	            }
	        }

	        t.commit();
	    } catch (Exception e) {
	        if (t != null) t.rollback();
	        System.out.println("Error al eliminar el empleado: " + e.getMessage());
	    } finally {
	        sesion.close();
	    }
	}


	// TODO Listar, Empleados con nombre de departamento. Sobrecargar método para que al pasar nombre de dpto se muestren empleados del mismo departamento (Utilizar HashSet de la clase Departamentos)

	private static HashSet<Empleados> listarEmpleados() {
		Session sesion = sf.openSession();
		String hql = "from Empleados e join fetch e.departamentos";
		
		TypedQuery<Empleados> consulta = sesion.createQuery(hql, Empleados.class);
		HashSet<Empleados> empleados = new HashSet<>(consulta.getResultList());

		sesion.close();
		return empleados;
	}
 
	private static HashSet<Empleados> listarEmpleados(String dpto) {
		Session sesion = sf.openSession();
		String hql = "from Empleados e join fetch e.departamentos where e.departamentos.dnombre = :dpto";
		TypedQuery<Empleados> consulta = sesion.createQuery(hql, Empleados.class)
				.setParameter("dpto", dpto);
		HashSet<Empleados> empleados = new HashSet<>(consulta.getResultList());

		sesion.close();
		return empleados;
	}

}
