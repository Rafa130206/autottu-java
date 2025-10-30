package br.com.fiap.autottu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.fiap.autottu.model.Checkin;
import br.com.fiap.autottu.model.Manutencao;
import br.com.fiap.autottu.model.Moto;
import br.com.fiap.autottu.model.Slot;
import br.com.fiap.autottu.model.TestRide;
import br.com.fiap.autottu.model.Usuario;
import br.com.fiap.autottu.repository.CheckinRepository;
import br.com.fiap.autottu.repository.ManutencaoRepository;
import br.com.fiap.autottu.repository.MotoRepository;
import br.com.fiap.autottu.repository.SlotRepository;
import br.com.fiap.autottu.repository.TestRideRepository;
import br.com.fiap.autottu.repository.UsuarioRepository;

@Service
public class CachingService {

	@Autowired
	private MotoRepository motoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private SlotRepository slotRepository;
	
	@Autowired
	private TestRideRepository testRideRepository;
	
	@Autowired
	private ManutencaoRepository manutencaoRepository;
	
	@Autowired
	private CheckinRepository checkinRepository;

	// ========== MOTOS ==========
	
	@Cacheable(value = "findAllMotos")
	public List<Moto> findAllMotos() {
		System.out.println("🔍 Buscando todas as motos do banco de dados...");
		return motoRepository.findAll();
	}

	@Cacheable(value = "findByIdMoto", key = "#id")
	public Optional<Moto> findByIdMoto(Integer id) {
		System.out.println("🔍 Buscando moto com ID " + id + " do banco de dados...");
		return motoRepository.findById(id);
	}

	@CacheEvict(value = { "findAllMotos", "findByIdMoto" }, allEntries = true)
	public void removerCacheMoto() {
		System.out.println("🗑️ Limpando cache das motos");
	}

	// ========== USUÁRIOS ==========

	@Cacheable(value = "findAllUsuarios")
	public List<Usuario> findAllUsuarios() {
		System.out.println("🔍 Buscando todos os usuários do banco de dados...");
		return usuarioRepository.findAll();
	}

	@Cacheable(value = "findByIdUsuario", key = "#id")
	public Optional<Usuario> findByIdUsuario(Integer id) {
		System.out.println("🔍 Buscando usuário com ID " + id + " do banco de dados...");
		return usuarioRepository.findById(id);
	}

	@CacheEvict(value = { "findAllUsuarios", "findByIdUsuario" }, allEntries = true)
	public void removerCacheUsuario() {
		System.out.println("🗑️ Limpando cache dos usuários");
	}

	// ========== SLOTS ==========
	
	@Cacheable(value = "findAllSlots")
	public List<Slot> findAllSlots() {
		System.out.println("🔍 Buscando todos os slots do banco de dados...");
		return slotRepository.findAll();
	}

	@Cacheable(value = "findByIdSlot", key = "#id")
	public Optional<Slot> findByIdSlot(Integer id) {
		System.out.println("🔍 Buscando slot com ID " + id + " do banco de dados...");
		return slotRepository.findById(id);
	}
	
	@Cacheable(value = "findSlotsDisponiveis")
	public List<Slot> findSlotsDisponiveis() {
		System.out.println("🔍 Buscando slots disponíveis do banco de dados...");
		return slotRepository.findByOcupadoFalse();
	}

	@CacheEvict(value = { "findAllSlots", "findByIdSlot", "findSlotsDisponiveis" }, allEntries = true)
	public void removerCacheSlot() {
		System.out.println("🗑️ Limpando cache dos slots");
	}

	// ========== TEST RIDES ==========
	
	@Cacheable(value = "findAllTestRides")
	public List<TestRide> findAllTestRides() {
		System.out.println("🔍 Buscando todos os test rides do banco de dados...");
		return testRideRepository.findAll();
	}

	@Cacheable(value = "findByIdTestRide", key = "#id")
	public Optional<TestRide> findByIdTestRide(Long id) {
		System.out.println("🔍 Buscando test ride com ID " + id + " do banco de dados...");
		return testRideRepository.findById(id);
	}

	@CacheEvict(value = { "findAllTestRides", "findByIdTestRide" }, allEntries = true)
	public void removerCacheTestRide() {
		System.out.println("🗑️ Limpando cache dos test rides");
	}

	// ========== MANUTENÇÕES ==========
	
	@Cacheable(value = "findAllManutencoes")
	public List<Manutencao> findAllManutencoes() {
		System.out.println("🔍 Buscando todas as manutenções do banco de dados...");
		return manutencaoRepository.findAll();
	}

	@Cacheable(value = "findByIdManutencao", key = "#id")
	public Optional<Manutencao> findByIdManutencao(Long id) {
		System.out.println("🔍 Buscando manutenção com ID " + id + " do banco de dados...");
		return manutencaoRepository.findById(id);
	}

	@CacheEvict(value = { "findAllManutencoes", "findByIdManutencao" }, allEntries = true)
	public void removerCacheManutencao() {
		System.out.println("🗑️ Limpando cache das manutenções");
	}

	// ========== CHECK-INS ==========
	
	@Cacheable(value = "findAllCheckins")
	public List<Checkin> findAllCheckins() {
		System.out.println("🔍 Buscando todos os check-ins do banco de dados...");
		return checkinRepository.findAll();
	}

	@Cacheable(value = "findByIdCheckin", key = "#id")
	public Optional<Checkin> findByIdCheckin(Integer id) {
		System.out.println("🔍 Buscando check-in com ID " + id + " do banco de dados...");
		return checkinRepository.findById(id);
	}

	@CacheEvict(value = { "findAllCheckins", "findByIdCheckin" }, allEntries = true)
	public void removerCacheCheckin() {
		System.out.println("🗑️ Limpando cache dos check-ins");
	}
}

