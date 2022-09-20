package com.example.webflux.web

import com.example.webflux.domain.Item
import com.example.webflux.service.ItemService
import com.example.webflux.service.data.ItemDTO
import com.example.webflux.service.data.ItemInfo
import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/items")
class ItemController
constructor(
    private val itemService: ItemService,
) {

    @PostMapping("")
    suspend fun created(@RequestBody dto: ItemDTO) = itemService.created(dto)

    @GetMapping()
    suspend fun getAll(): Flow<Item> = itemService.getAll()

    @GetMapping("/{id}")
    suspend fun get(@PathVariable id: Long): ResponseEntity<Item> = itemService.get(id).let {
        if (it == null) ResponseEntity.noContent().build() else ResponseEntity.ok().body(it)
    }

    @GetMapping("/name")
    suspend fun get(name: String): ResponseEntity<ItemInfo> = itemService.get(name).let {
        if (it == null) ResponseEntity.noContent().build() else ResponseEntity.ok().body(it)
    }
}