package database;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import modelo.CategoriaHabitacion;
import modelo.Ciudad;
import modelo.Empresa;
import modelo.Habitacion;
import modelo.Hotel;
import modelo.Pago;
import modelo.Reserva;
import modelo.Usuario;
import utils.HibernateUtil;
import utils.Log;
import utils.Notificador;

/***
 * Clase base de datos. Esta clase nos permite realizar todas
 * las operaciones relacionadas con la BD. He tomado la decisión 
 * de que esta clase tenga el patrón de diseño Singleton, ya que esta clase
 * no debe instanciarse muchas veces, debido a que abre las conexiones con la BD.
 * @author cesco
 *
 */
public class Database {

	private static Database instance = null;

	private List<Usuario> usuarios;
	private List<Ciudad> ciudades;
	private List<Hotel> hoteles;
	private SessionFactory sf;
	private Session session;

	// Metodos para la base de datos.
	public static Database getInstance() {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}

	/***
	 * Abre la SessionFactory
	 */
	private Database() {
		this.sf = HibernateUtil.getSessionFactory();		
	}

	/***
	 * Este metodo cierra la sessionFactory. Solo se debe utilizar cuando hemos
	 * terminado las operaciones con la instancia.
	 */
	public void close() {
		this.sf.close();
	}

	/***
	 * Abre la sesion para iniciar transacciones
	 */
	private void openSession() {
		this.session = sf.openSession();
	}

	/**
	 * Cierra la sesion una vez hechas las transacciones
	 */
	private void closeSession() {
		this.session.close();
	}


	/***
	 * Metodo para añadir usuarios a la lista de usuarios
	 * 
	 * @param usuario usuario que se va a añadir a la lista.
	 */
	public void addUsuario(Usuario usuario) {
		if (!usuarios.contains(usuario)) {
			usuarios.add(usuario);
		}
	}

	/***
	 * Insertar usuarios en la base de datos.
	 * 
	 * @param usuario El usuario a insertar
	 * @return verdadero si se ha insertado
	 */
	public boolean insertUsuario(Usuario usuario) {
		this.usuarios = getUsuarios();
		if (!usuarios.contains(usuario)) {
			this.addUsuario(usuario);
			openSession();
			session.getTransaction().begin();
			session.save(usuario);
			session.getTransaction().commit();
			closeSession();
			return true;
		}
		return false;
	}

	/***
	 * Metodo que busca un usario en una lista de Usuarios por correo y contrasena.
	 * Un usuario es igual a otro si su correo y contraseña con los mismos.
	 * @param email Email del usuario.
	 * @param contrasena Contraseña del usuario.
	 * @return Un usuario si existe un usuario con esos parámetros, null si no.
	 */
	public Usuario selectUsuarioByEmailAndContrasena(String email, String contrasena) {
		this.usuarios = this.loadUsuarios();
		Usuario usuario = new Usuario(null, email, contrasena);
		if (this.usuarios.contains(usuario)) {
			return usuarios.get(usuarios.indexOf(usuario));
		}
		return null;
	}

	/***
	 * Método que carga todos los usuarios en una Lista.
	 * @return Una lista de Usuarios.
	 */
	private List<Usuario> loadUsuarios() {
		openSession();
		Query<Usuario> query = session.createQuery("from Usuario");
		// this.usuarios = query.list();
		List<Usuario> usuarios = query.list();
		closeSession();
		return usuarios;

	}
	
	public List<Usuario> getUsuarios(){
		return this.loadUsuarios();
	}


	/***
	 * Método que carga todas las ciudades en una lista.
	 * @return Una lista de ciudades.
	 */
	private List<Ciudad> loadCiudades() {
		openSession();
		Query<Ciudad> query = session.createQuery("from Ciudad");
		List<Ciudad> ciudades = query.list();
		closeSession();
		// this.ciudades = ciudades;
		return ciudades;
	}

	public List<Ciudad> getCiudades() {
		return loadCiudades();
	}

	/***
	 * Método que carga todos los hoteles en una lista.
	 * @return Una lista de hoteles.
	 */
	public List<Hotel> loadHoteles() {
		openSession();
		Query<Hotel> query = session.createQuery("from Hotel");
		List<Hotel> hoteles = query.list();
		closeSession();
		return hoteles;
	}

	public List<Hotel> getHoteles() {
		return loadHoteles();
	}

	/***
	 * Inner join sobre la tabla Hoteles y la tabla Ciudades
	 * @return una Lista con el resultado de la query.
	 */
	public List getHotelesCiudades() {
		openSession();
		Query query = session.createQuery(
				"SELECT H.nombre, H.categoria, C.nombre, C.pais FROM Hotel H INNER JOIN H.ciudad C ORDER BY H.nombre");
		List objects = query.list();
		closeSession();
		return objects;
	}

	/***
	 * Método que selecciona un hotel.
	 * @param nombre Nombre del hotel que se quiere seleccionar.
	 * @return Un hotel si existe, null si no.
	 */
	public Hotel selectHotel(String nombre) {
		openSession();
		Query query = session.createQuery("FROM Hotel H WHERE H.nombre IN (:nombres)");
		query.setParameter("nombres", nombre);
		List<Hotel> list = query.list();
		closeSession();
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/***
	 * Método que selecciona todas las Habitaciones de la bd.
	 * @return Una Lista de Habitaciones.
	 */
	private List<Habitacion> loadHabitaciones() {
		openSession();
		Query<Habitacion> query = session.createQuery("from Habitacion");
		List<Habitacion> hoteles = query.list();
		closeSession();
		return hoteles;
	}

	public List<Habitacion> getHabitaciones() {
		return loadHabitaciones();
	}

	
	/***
	 * Método que selecciona todas las Habitaciones pertenecientes a un hotel.
	 * @param h Hotel sobre el que se van a buscar las habitaciones
	 * @return una Lista de Habitaciones.
	 */
	public List<Habitacion> getHabitaciones(Hotel h) {
		List<Habitacion> list = loadHabitaciones();
		ListIterator<Habitacion> listIterator = list.listIterator();
		int i = 0;
		while (listIterator.hasNext()) {
			if (!listIterator.next().getHotel().equals(h)) {
				listIterator.remove();
			}
		}
		return list;
	}

	/***
	 * Método que selecciona las habitaciones y las agrupa según su categoria.
	 * @param h Hotel del que se van a seleccionar las habitaciones.
	 * @return Una Lista con el resultado de la query
	 */
	public List getHabitacionesAgrupadas(Hotel h) {
		openSession();
		Query query = session.createQuery("select categoriaHabitacion, precio, count(categoriaHabitacion) from Habitacion "
				+ "where hotel=:hotel group by categoriaHabitacion");
		query.setParameter("hotel", h);
		List list = query.list();
		closeSession();
		return list;
		
	}
	/***
	 * Método que busca si en la base de datos una habitación está ocupada.
	 * @param reserva Reserva que se quiere insertar, pero aún no sabemos si es posible.
	 * @return falso, si la habitación no está ocupada en esa fecha, verdadero si sí.
	 */
	public boolean habitacionOcupada(Reserva reserva) {
		openSession();
		try {
			Query query = session.createQuery("select count(*) from Reserva where "
					+ "habitacion=:habitacion and fechaEntrada<=:fechOut and fechaSalida>:fechIn");
			query.setParameter("habitacion", reserva.getHabitacion());
			query.setParameter("fechIn", reserva.getFechaEntrada());
			query.setParameter("fechOut", reserva.getFechaSalida());
			Long count = (Long)query.uniqueResult();
			closeSession();
			if(count<=0) {
				return false;
			}
			return true;	
			
		} catch(HibernateException e){
			Notificador.notificaError("Ocurrió un error. Contacte con el administrador.");
			Log.i("Database lanzó una excepción: ");
			Log.i(e.getMessage());
		} finally {
			if (this.session != null) {
				closeSession();
			}		
		}
		return true;
	}
	
	/***
	 * Método que selecciona todas las reservas de la Base de datos.
	 * @return Una Lista de Reservas.
	 */
	private List<Reserva> loadReservas(){
		openSession();
		Query<Reserva> query = session.createQuery("from Reserva");
		List<Reserva> list = query.list();
		closeSession();
		return list;		
	}
	
	public List<Reserva> getReservas(){
		return this.loadReservas();
	}
	
	public Reserva getReserva(int id) {
		openSession();
		Reserva r = session.get(Reserva.class, (int)id);
		closeSession();
		return r;
	}
	
	public void updateReserva(Reserva reserva) {
		
		Reserva r = getReserva(reserva.getIdReserva());
		openSession();
		session.getTransaction().begin();
		r.setPrecio(reserva.getPrecio());
		r.setFechaEntrada(reserva.getFechaEntrada());
		r.setFechaSalida(reserva.getFechaSalida());
		try {
			session.update(r);
			session.getTransaction().commit();
			closeSession();
		} catch (HibernateException e) {
			try {
				session.getTransaction().rollback();
			} catch(RuntimeException ex) {
				Log.i(ex.getMessage());
			}
			Log.i("Hibernate: \n"+e.getMessage());
		} finally {
			if (this.session != null) {
				closeSession();
			}
		}
		
	}
	/***
	 * Método que nos devuelve la reserva que ha impedido otra reserva.
	 * @param reserva Reserva que se quería realizar.
	 * @return La reserva original.
	 */
	public Reserva selectReservaOcupada(Reserva reserva) {
		openSession();		
		Query<Reserva> query = session.createQuery("from Reserva where habitacion=:habitacion and fechaEntrada<=:fechOut and fechaSalida>:fechIn");		
		query.setParameter("habitacion", reserva.getHabitacion());
		query.setParameter("fechIn", reserva.getFechaEntrada());
		query.setParameter("fechOut", reserva.getFechaSalida());
		List<Reserva> list = query.list();
		closeSession();
		
		return list.get(0);
	}

	/***
	 * Método que inserta una reserva en la BD.
	 * @param reserva Reserva a insertar.
	 */
	public void insertReserva(Reserva reserva) {
		openSession();
		session.getTransaction().begin();
		try {
			session.save(reserva);
			session.getTransaction().commit();
			closeSession();
		} catch(HibernateException e) {
			try {
				session.getTransaction().rollback();
			} catch(RuntimeException ex) {
				
			}
			Log.i(e.getMessage());
		} finally {
			if (this.session != null) {
				closeSession();
			}
		}
		
	}
	
	public void insertPago(Pago pago) {
		openSession();
		session.getTransaction().begin();
		try {
			session.save(pago);
			session.getTransaction().commit();
			closeSession();
		} catch(HibernateException e) {
			try {
				session.getTransaction().rollback();
			} catch(RuntimeException ex) {
				
			}
			Log.i(e.getMessage());
		} finally {
			if (this.session != null) {
				closeSession();
			}
		}
		
	}
	/***
	 * Método que borra una reserva de la BD.
	 * @param reserva Reserva a borrar.
	 */
	public void deleteReserva(Reserva reserva) {
		openSession();		
		int id = reserva.getIdReserva();
		Query query = session.createQuery("DELETE FROM Reserva where idReserva in(:id)");
		query.setParameter("id", id);
		session.getTransaction().begin();
		try {
			query.executeUpdate();
			session.getTransaction().commit();
			closeSession();		
		} catch(HibernateException e) {
			try {
				session.getTransaction().rollback();
			} catch(RuntimeException ex) {
				
			}
			Log.i(e.getMessage());
		} finally {
			if (this.session != null) {
				closeSession();
			}
		}
		
		
	}
	

	/***
	 * Método que nos permite ejecutar una query en la BD.
	 * @param query Query a ejecutar.
	 * @return una lista con el resultado.
	 */
	public List query(String query) {
		openSession();
		List l = session.createQuery(query).list();
		closeSession();
		return l;
	}
	
	/***
	 * Metodo que permite ejecutar una query en lenguaje SQL
	 * @param query Query a ejecutar.
	 * @return Una lista con el resultado.
	 */
	public List nativeQuery(String query) {
		openSession();
		NativeQuery nativeQuery = session.createSQLQuery(query);	
		List result = nativeQuery.list();
		return result;
	}
	
	/***
	 * Método que recupera una Habitación por su id.
	 * @param id PK de la habitación
	 * @return una habitación.
	 */
	public Habitacion getHabitacion(int id) {
		openSession();
		Habitacion h = session.get(Habitacion.class, id);
		closeSession();
		return h;
	}
	
	/***
	 * Método que carga todas las empresas de la BD
	 * @return una Lista de Empresas.
	 */
	private List<Empresa> loadEmpresas(){
		openSession();
		Query<Empresa> query = session.createQuery("from Empresa");
		List<Empresa> list = query.list();
		closeSession();
		return list;		
	}
	
	public List<Empresa> getEmpresas() {
		return this.loadEmpresas();
	}

}
